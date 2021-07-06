import COS from "cos-js-sdk-v5";
import httpRequest from '@/utils/httpRequest' // api: https://github.com/axios/axios
import { Message } from 'element-ui';

export function generateUUID() {
  var d = new Date().getTime();
  var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = (d + Math.random()*16)%16 | 0;
    d = Math.floor(d/16);
    return (c=='x' ? r : (r&0x3|0x8)).toString(16);
  });
  return uuid;
};


export function cospush(name,file){
  var da = null;
  httpRequest({
    url: httpRequest.adornUrl(`/third-party/ten/getTempKey`),
    method: 'get',
  }).then(({data}) => {
    if (data && data.code === 0) {
      let d= JSON.parse(data.data)
      console.log(d)
      let cos= new  COS({
        SecretId: d.credentials.tmpSecretId,
        SecretKey: d.credentials.tmpSecretKey,
        SecurityToken:d.credentials.sessionToken
      })
      console.log(cos)
      cos.putObject({
        Key: name,
        Bucket:"theangel-1306086135",
        Region:"ap-guangzhou",
        Body: file, // 上传文件对象
      },function (err, data){
        da=err || data
        console.log(err || data);
      })
    }
  })

  if(!da || da.statusCode!=200){
    Message.error('图片上传失败！');
  }

  return da;
};

