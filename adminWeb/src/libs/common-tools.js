/**
 * 公共 工具
 * 常用工具
 */
// 时间格式化
export function formatDate (date, fmt) {
  let o = {
    'M+': date.getMonth() + 1, // 月份
    'd+': date.getDate(), // 日
    'h+': date.getHours(), // 小时
    'm+': date.getMinutes(), // 分
    's+': date.getSeconds(), // 秒
    S: date.getMilliseconds() // 毫秒
  }
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(
      RegExp.$1,
      (date.getFullYear() + '').substr(4 - RegExp.$1.length)
    )
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length)
      )
    }
  }
  return fmt
}

// 是否是闰年
export function isLeapYear (year) {
  if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
    return true
  } else {
    return false
  }
}

// 获取月的最大天数,month 从1开始
export function getMonthMaxDays (year, month) {
  switch (month) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
      return 31
    case 4:
    case 6:
    case 9:
    case 11:
      return 30
    default:
      if (isLeapYear(year)) {
        return 29
      }
      return 28
  }
}

// 设置时间
export function setTimeValue (date, isTime) {
  if (isTime) {
    date.setHours(23)
    date.setMinutes(59)
    date.setSeconds(59)
    date.setMilliseconds(999)
  } else {
    date.setHours(0)
    date.setMinutes(0)
    date.setSeconds(0)
    date.setMilliseconds(0)
  }
  return date
}

// 获取季度顺序1表示第一个季度，4表示第四个季度 month开始为1
export function getQuarterOrder (month) {
  if (month >= 1 && month <= 3) {
    return 1
  } else if (month >= 4 && month <= 6) {
    return 2
  } else if (month >= 7 && month <= 9) {
    return 3
  }
  return 4
}

// 获取月初
export function getMonthBegin () {
  let date = new Date()
  date.setDate(1)
  setTimeValue(date, false)
  return date
}

// 获取月底
export function getMonthEnd (isTime) {
  let date = new Date()
  date.setDate(getMonthMaxDays(date.getFullYear, date.getMonth() + 1))
  setTimeValue(date, isTime)
  return date
}

// 获取上月初
export function getPreviousMonthBegin () {
  let date = new Date()
  if (date.getMonth() === 0) {
    date.setFullYear(date.getFullYear() - 1)
    date.setMonth(11)
  } else {
    date.setMonth(date.getMonth() - 1)
  }
  date.setDate(1)
  setTimeValue(date, false)
  return date
}

// 获取上月底
export function getPreviousMonthEnd (isTime) {
  let date = new Date()
  if (date.getMonth() === 0) {
    date.setFullYear(date.getFullYear() - 1)
    date.setMonth(11)
  } else {
    date.setMonth(date.getMonth() - 1)
  }
  date.setDate(getMonthMaxDays(date.getFullYear, date.getMonth() + 1))
  setTimeValue(date, isTime)
  return date
}

// 获取本季初
export function getQuarterBegin () {
  let date = new Date()
  let order = getQuarterOrder(date.getMonth + 1)
  if (order === 1) {
    date.setMonth(0)
  } else if (order === 2) {
    date.setMonth(3)
  } else if (order === 3) {
    date.setMonth(6)
  } else {
    date.setMonth(9)
  }
  date.setDate(1)
  setTimeValue(date, false)
  return date
}

// 获取本季底
export function getQuarterEnd (isTime) {
  let date = new Date()
  let order = getQuarterOrder(date.getMonth + 1)
  if (order === 1) {
    date.setMonth(2)
    date.setDate(31)
  } else if (order === 2) {
    date.setMonth(5)
    date.setDate(30)
  } else if (order === 3) {
    date.setMonth(8)
    date.setDate(30)
  } else {
    date.setMonth(11)
    date.setDate(31)
  }
  setTimeValue(date, isTime)
  return date
}

// 获取本年初
export function getYearBegin () {
  let date = new Date()
  date.setMonth(0)
  date.setDate(1)
  setTimeValue(date, false)
  return date
}

// 获取本年底
export function getYearEnd (isTime) {
  let date = new Date()
  date.setMonth(11)
  date.setDate(31)
  setTimeValue(date, isTime)
  return date
}
