# theangelmall


#### 介绍

#### 软件架构
1. 软件架构说明

#### 版本

1.  python 3.x
2.  nodejs 13.x

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献



#### 商品模块

SPU：standard product unit(标准化产品单元)：是商品信息聚合的最小单位，是一组可复用、易检索的标准化信息的集合，该集合描述了一个产品的特性。如iphoneX是SPU

SKU：stock keeping unit(库存量单位)：库存进出计量的基本单元，可以是件/盒/托盘等单位。SKU是对于大型连锁超市DC配送中心物流管理的一个必要的方法。现在已经被引申为产品统一编号的简称，每种产品对应有唯一的SKU号。

如iphoneX 64G 黑色 是SKU
基础属性：同一个SPU拥有的特性叫基本属性。如机身长度，这个是手机共用的属性。而每款手机的属性值不同

也可以叫规格参数
销售属性：能决定库存量的叫销售属性。如颜色

3、基本属性〖规格参数〗与销售属性
每个分类下的商品共享规格参数，与销售属性。只是有些商品不一定要用这个分类下全部的属性；

属性是以三级分类组织起来的
规格参数中有些是可以提供检索的
规格参数也是基本属性，他们具有自己的分组
属性的分组也是以三级分类组织起来的
属性名确定的，但是值是每一个商品不同来决定的



# elasticSearch

创建商品索引映射

```json
PUT product
{
    "mappings":{
        "properties": {
            "skuId":{ "type": "long" },
            "spuId":{ "type": "keyword" },   ## 不可分词
            "skuTitle": {
                "type": "text",
                "analyzer": "ik_smart"  # 中文分词器
            },
            "skuPrice": { "type": "keyword" },  # 保证精度问题
            "skuImg"  : {
       		 "type": "keyword",
        	 "index":false,
        	 "doc_values":false
    		},  
            "saleCount":{ "type":"long" },
            "hasStock": { "type": "boolean" },
            "hotScore": { "type": "long"  },
            "brandId":  { "type": "long" },
            "catalogId": { "type": "long"  },
            "brandName": {
                "type": "keyword"
            },  
            "brandImg":{
                "type": "keyword",
                "index": false,  # 不可被检索，不生成index，只用做页面使用
                "doc_values": false # 不可被聚合，默认为true
            },
            "catalogName": {"type": "keyword" }, # 视频里有false
            "attrs": {
                "type": "nested",
                "properties": {
                    "attrId": {"type": "long"  },
                    "attrName": {
                        "type": "keyword",
                        "index": false,
                        "doc_values": false
                    },
                    "attrValue": {"type": "keyword" }
                }
            }
        }
    }
}
```

