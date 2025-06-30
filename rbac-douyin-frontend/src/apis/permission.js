import request from './request.js'

export const apiGetMenus = () => {
    return request.get('http://localhost:8882/authorize/permission/initMenu')
}

export const apiGetPermissions = () => {
    return request.get('http://localhost:8882/authorize/permission/list')
}

export const apiGetPermissionCascader = () => {
    return request.get('http://localhost:8882/authorize/permission/treeSelect')
}

export const apiAddPermission = (data) => {
    return request.post('http://localhost:8882/authorize/permission/', data)
}

export const apiEditPermission = (data) => {
    return request.put('http://localhost:8882/authorize/permission/', data)
}

export const apiDeletePermission = (id) => {
    return request.delete(`http://localhost:8882/authorize/permission/${id}`)
}

// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

// export const apiGetMenus = () => {
//     return request.get('http://localhost:8080/authorize/permission/treeSelect')
// }

// export const apiGetPermissions = () => {
//     return request.get('http://localhost:8080/authorize/permission/treeSelect')
// }

// export const apiGetPermissionCascader = () => {
//     return request.get('http://localhost:8080/authorize/permission/treeSelect')
// }

// export const apiAddPermission = (data) => {
//     return request.post('http://localhost:8080/addtest', data)
// }

// export const apiEditPermission = (data) => {
//     return request.put('http://localhost:8080/authorize/permission/update', data)
// }

// export const apiDeletePermission = (id) => {
//     return request.delete(`http://localhost:8080/authorize/permission/delete/${id}`)
// }
