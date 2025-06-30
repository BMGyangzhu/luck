import request from './request.js'

export const apiGetUsers = (data)  => {
    return request.get('http://localhost:8882/admin/user/page',{
        params: {
            page: data.page,
            limit: data.limit
        }
    })
}

export const apiAddUser = (data) => {
    return request.post('http://localhost:8882/admin/user/', data)
}

export const apiEditUser = (data) => {
    return request.put('http://localhost:8882/admin/user/', data);
}

export const apiDeleteUser = (id) => {
    return request.delete(`http://localhost:8882/admin/user/${id}`)
}