import request from './request.js'

// export const apiGetRolePermission = (id) => {
//     return request.get(`http://localhost:8080/authorize/role/getPermission/${id}`)
// }

// export const apiAssignRolePermission = (rid,permissionIds) => {
//     return request.post('http://localhost:8080/authorize/role/authority',{
//     rid,
//     pid: permissionIds
//     })
// }

// export const apiGetRoles = () => {
//     return request.get('http://localhost:8080/authorize/role/list')
// }

// export const apiInitRole = () => {
//     return request.get('http://localhost:8080/authorize/role/initRole')
// }

// export const apiAssignRole = (uid, rids) => {
//   return request.post('http://localhost:8080/authorize/role/assignRole', {
//     uid: uid,
//     rid: rids
//   })
// }

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
export const apiGetRolePermission = (id) => {
    return request.get(`http://localhost:8882/authorize/role/getPermission/${id}`)
}

export const apiAssignRolePermission = (rid,permissionIds) => {
    return request.post('http://localhost:8882/authorize/role/authority',{
    rid,
    pid: permissionIds
    })
}

export const apiGetRoles = () => {
    return request.get('http://localhost:8882/authorize/role/list')
}

export const apiInitRole = () => {
    return request.get('http://localhost:8882/authorize/role/initRole')
}

export const apiAssignRole = (uid, rids) => {
  return request.post('http://localhost:8882/authorize/role/assignRole', {
    uid: uid,
    rid: rids
  })
}

export const apiDeleteRole = (id) => {
    return request.delete(`http://localhost:8882/authorize/role/${id}`)
}

