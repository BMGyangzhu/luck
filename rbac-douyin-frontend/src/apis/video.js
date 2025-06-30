import request from './request.js'

export const apiGetVideos = (data) => {
  return request.get('http://localhost:8882/admin/video/page', {
    params: {
      page: data.page,
      limit: data.limit,
      YV: data.YV || '',       // 传入 YV 搜索条件
      title: data.title || ''  // 传入 title 搜索条件
    }
  })
}

export const apiEditVideo = (data) => {
    return request.put('http://localhost:8882/admin/video/', data)
}

export const apiDeleteVideo = (id, userId) => {
    return request.delete(`http://localhost:8882/admin/video/${id}/${userId}`)
}

export const apiAuditVideo = (data) => {
    return request.post('http://localhost:8882/admin/video/audit', data)
} 


export const apiViolation = (id) => {
    return request.post(`http://localhost:8882/admin/video/violations/${id}`)
}