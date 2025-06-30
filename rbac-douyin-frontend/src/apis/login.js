import request from './request.js'

export const apiLogin = (data) => {
    return request.post('http://localhost:8882/luckyjourney/login', {
        email: data.email,
        password: data.password
    })
}

// export const apiLogin = (data) => {
//   return request.post('http://localhost:8080/login', data) // data 是 JSON
// }