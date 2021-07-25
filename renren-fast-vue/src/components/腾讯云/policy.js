import http from '@/utils/httpRequest.js'

export function policy() {
  return new Promise((resolve, reject) => {
    http({
      // url: http.adornUrl("/thirdparty/oss/policy"),
      url: http.adornUrl("/third-party/ten/getTempKey"),
      method: "get",
      params: http.adornParams({})
    }).then(({data}) => {
      resolve(data);
    })
  });
}
