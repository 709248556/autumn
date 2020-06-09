# 后台管理系统


### 解决上传需登录的问题
修改 public/ueditor/dialogs/attachment.js  171行  WebUploader.create 中添加 withCredentials: true
修改 public/ueditor/dialogs/images.js  366行  WebUploader.create 中添加 withCredentials: true
修改 public/ueditor/dialogs/video.js  390行  WebUploader.create 中添加 withCredentials: true