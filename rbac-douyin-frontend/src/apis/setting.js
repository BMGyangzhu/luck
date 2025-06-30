import request from './request.js'

export const apiGetSetting = () => {
    return request.get('http://localhost:8882/admin/setting')
}

export const apiEditSetting = (data) => {
    return request.put('http://localhost:8882/admin/setting/', data)
}