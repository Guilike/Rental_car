import { get, post, upload } from './index'

export const Login = function (params) {
  return post('/login/check', params)
}

export const Register = function (params) {
  return post('/login/register', params)
}

export const Logout = function (params) {
  return post('/login/logout', params)
}

export const CustomerInfoList = function () {
  return get('/customerInfo/list')
}

export const CustomerSearch = function (type, searchText) {
  return get('/customerInfo/search', {
    params: {
      type: type,
      searchText: searchText
    }
  })
}

export const CustomerAdd = function (params) {
  return post('/customerInfo/add', params)
}

export const CustomerDelete = function (params) {
  return post('/customerInfo/delete', params)
}

export const CustomerDelIds = function (params) {
  return post('/customerInfo/deleteIds', params)
}

export const CustomerUpdate = function (params) {
  return post('/customerInfo/update', params)
}

export const getByIdentity = function(identity) {
  return get('/customerInfo/listByidentity', {
    params: {
      identity: identity
    }
  })
}

export const Carlist = function () {
  return get('/car/list')
}

export const CarOutList = function() {
  return get('/car/outList')
}

export const CarType = function() {
  return get('/car/type')
}
export const CarBrand = function() {
  return get('/car/brand')
}
export const CarColor = function() {
  return get('/car/color')
}
export const CarAddress = function() {
  return get('/car/address')
}

export const CarDelete = function (params) {
  return post('/car/delete', params)
}

export const CarDelIds = function (params) {
  return post('/car/deleteIds', params)
}

export const CarAdd = function (params) {
  return post('/car/add', params)
}

export const CarUpdate = function (params) {
  return post('/car/update', params)
}

export const CarSearch = function(searchText) {
  return get('/car/search', {
    params: {
      searchText: searchText
    }
  })
}

export const searchAddress = function(address) {
  return get('/car/searchAddress', {
    params: {
      address: address
    }
  })
}

export const getByNumber = function(number) {
  return get('/car/listBynum', {
    params: {
      number: number
    }
  })
}

export const IndentpriceAndDays = function(params) {
  return post('/indent/price', params)
}

export const IndentAdd = function(params) {
  return post('/indent/add', params)
}

export const IndentList = function() {
  return get('/indent/list')
}

export const IndentSearch = function (type, searchText, timevalue) {
  return get('/indent/search', {
    params: {
      type: type,
      searchText: searchText,
      timevalue: timevalue
    }
  })
}

export const IndentDelIds = function (params) {
  return post('/indent/deleteIds', params)
}


export const IndentUserList = function(identity) {
  return get('/indent/userList', {
    params: {
      identity: identity
    }
  })
}

export const IndentUpdate = function (params) {
  return post('/indent/update', params)
}

export const IndentCancel = function(params) {
  return post('/indent/cancel', params)
}

export const CheckList = function () {
  return get('/check/list')
}

export const CheckAdd = function (params) {
  return post('/check/add', params)
}

export const CheckUpdate = function (params) {
  return post('/check/update', params)
}

export const CheckDelete = function (params) {
  return post('/check/delete', params)
}

export const CheckSearch = function (type, searchText) {
  return get('/check/search', {
    params: {
      type: type,
      searchText: searchText
    }
  })
}

export const getByOrderId = function(orderid) {
  return get('/check/listByOrderId', {
    params: {
      orderid: orderid
    }
  })
}

export const ManageList = function() {
  return get('/manage/list')
}

export const ManageAdd = function(params) {
  return post('/manage/add', params)
}

export const ManageDelete = function(params) {
  return post('/manage/delete', params)
}

export const ManageDeleteIds = function(params) {
  return post('/manage/deleteIds', params)
}

export const ManageUpdate = function(params) {
  return post('/manage/update', params)
}

export const ManageSearch = function (type, searchText) {
  return get('/manage/search', {
    params: {
      type: type,
      searchText: searchText
    }
  })
}

export const ManageResetPwd = function (id) {
  return get('/manage/resetpwd', {
    params: {
      id: id
    }
  })
}

export const PersonInfo = function (username) {
  return get('/person/info', {
    params: {
      username: username
    }
  })
}

export const PersonUpdate = function(params) {
  return post('/person/updateInfo', params)
}

export const PersonUpdatePassword = function(params) {
  return post('/person/updatePwd', params)
}