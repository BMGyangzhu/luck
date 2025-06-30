import request from './request.js'

export const apiGetTypes = (data)  => {
    return request.get('http://localhost:8882/admin/type/page',{
        params: {
            page: data.page,
            limit: data.limit
        }
    })
}

export const apiAddType = (data) => {
    return request.post('http://localhost:8882/admin/type/', data)
}

export const apiEditType = (data) => {
    return request.put('http://localhost:8882/admin/type/', data);
}

export const apiDeleteType = (id) => {
    return request.delete(`http://localhost:8882/admin/type/${id}`)
}