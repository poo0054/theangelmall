package com.themall.product.web;

import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.product.entity.CategoryEntity;
import com.themall.product.service.CategoryService;
import com.themall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class indexController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @GetMapping({"/", "/index.html", "/index"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> indexCatalog(Model model) {
        Map<String, List<Catelog2Vo>> categoryEntities = categoryService.getCatalogJson();
        model.addAttribute("categorys", categoryEntities);
        return categoryEntities;
    }

    @ResponseBody
    @GetMapping("/lock")
    public String lock(Model model) {
        //获得锁
        RLock lock = redissonClient.getLock("lock");
        /**
         * 加锁 阻塞式的锁
         *      锁会自动续期 如果业务时间过长 （看门狗时间为默认时间的1/3），10秒后续期， 会自动改为30秒  不用担心业务过长而把锁删除  默认加的锁为30秒
         *      加锁的业务，只要运行完成，就不会自动续期，即使没有手动解锁    锁也会在30秒后自动删除
         */
//        lock.lock();

        /**
         * 设置自动解锁时间，一定要大于业务时间
         * 问题：在锁时间到了，不会自动续期，业务时间大于指定时间时，会解锁下一个线程的锁。会抛出异常
         */
        lock.lock(10, TimeUnit.SECONDS);

        /**
         * 推荐使用指定时间，省略整个续期操作。手动解锁
         *  超时时间的值给大
         */
        try {
            System.out.println("我获得了锁......" + Thread.currentThread().getId());
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁，  解锁代码没有运行  redisson也不会产生死锁
            System.out.println("我释放了锁......" + Thread.currentThread().getId());
            lock.unlock();
        }

        return "我是在测试锁";
    }

    String name = "read-write";

    /**
     * 写锁 排它锁（互斥锁）
     * 保证一定能够读到最新数据， 每次只能有一个写锁存在，只有写完了才能够读取
     * 有一个读锁在运行  写锁也需要等待
     *
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/write")
    public String write(Model model) {
        String uuid = null;
        /**
         * 获取读写锁只要名称一样  就是一把锁
         */

        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(name);
        RLock rLock = readWriteLock.writeLock();
        try {
            //读写锁
            rLock.lock();
            uuid = UUIDUtils.getUUID();
            stringRedisTemplate.opsForValue().set("write", uuid);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return uuid;
    }

    /**
     * 读锁 共享锁
     * 保证一定能够读到最新数据，修改期间读锁需要等写锁释放才能读到
     * 当前读锁存在，写锁也需要等待读锁释放才能够写
     *
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/read")
    public String read(Model model) {
        String write = null;
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(name);
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            write = stringRedisTemplate.opsForValue().get("write");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return write;
    }

    String door = "door";

    /**
     * 闭锁
     * 只有5个班的人走完了，才能放假
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/lockdoor")
    public String lockDoor() throws InterruptedException {
        //获取闭锁
        RCountDownLatch door = redissonClient.getCountDownLatch(this.door);
        //等5个班的人走完了才放假
        door.trySetCount(5);
        //等待闭锁都完成
        door.await();
        return "门卫把学校门锁了，放假了......";
    }

    /**
     * 闭锁，走一个减一
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/gogogo/{id}")
    public String gogogo(@PathVariable("id") Long id) {
        RCountDownLatch door = redissonClient.getCountDownLatch(this.door);
        door.countDown();
        return id + "班的人都走了";
    }


    /**
     * 型号量
     * 3个车位，走一个车才能进来一个车
     * 停车的请求
     * TODO 可以用来做限流，把最大流量放入，每次tryAcquire获取是否还有流量
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        //创建一个信号量
        RSemaphore park = redissonClient.getSemaphore("park");
        //获取一个信号量，获取一个值 一直等待可以停车
        park.acquire();
        //加上try是非阻塞的，获取不到就不会继续等待 会返回是否能成功
//        park.tryAcquire();
        return "ok......";
    }

    /**
     * 开走
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/go")
    public String go() {
        RSemaphore park = redissonClient.getSemaphore("park");
        //释放一个车位， 丢掉一个，其余才能获取
        park.release();
        return "车开走了";
    }


}

