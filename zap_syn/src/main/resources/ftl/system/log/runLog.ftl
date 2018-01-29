<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>日志</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
          charset="utf-8"></script>
  <style>
    .layui-input {
      height: 30px;
      width: 120px;
    }

    .x-nav {
      padding: 0 20px;
      position: relative;
      z-index: 99;
      border-bottom: 1px solid #e5e5e5;
      height: 32px;
      overflow: hidden;
    }
  </style>
</head>

<body>
<br/>
<div class="x-nav">
  <div class="select">
    操作用户：
    <div class="layui-inline">
      <input class="layui-input" height="20px" id="userName" autocomplete="off">
    </div>
    操作类型：
    <div class="layui-inline">
      <input class="layui-input" height="20px" id="type" autocomplete="off">
    </div>
    <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
    </button>
    <button class="layui-btn layui-btn-sm" id="refresh" style="float: right;"
            data-type="reload">
      <i class="layui-icon">ဂ</i>
    </button>
  </div>
</div>

<table id="logfile" class="layui-hide" lay-filter="log"></table>
<script type="text/html" id="toolBar">
  <a class="layui-btn  layui-btn-xs" lay-event="look"><i class="layui-icon">&#xe60a;</i>查看</a>
</script>
<script>
  layui.laytpl.toDateString = function(d, format){
    var date = new Date(d || new Date())
        ,ymd = [
      this.digit(date.getFullYear(), 4)
      ,this.digit(date.getMonth() + 1)
      ,this.digit(date.getDate())
    ]
        ,hms = [
      this.digit(date.getHours())
      ,this.digit(date.getMinutes())
      ,this.digit(date.getSeconds())
    ];

    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
    .replace(/MM/g, ymd[1])
    .replace(/dd/g, ymd[2])
    .replace(/HH/g, hms[0])
    .replace(/mm/g, hms[1])
    .replace(/ss/g, hms[2]);
  };

  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };

  document.onkeydown = function (e) { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
      $(".select .select-on").click();
    }
  }

  layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
      id: 'logfile',
      elem: '#logfile'
      , url: '/error/logdir'
      , cols: [[
          {checkbox: true, fixed: true, width: '5%'}
        , {field: 'name', title: '文件名称', sort: true}
        , {field: 'path', title: '文件路径',  sort: true}
        , {field: 'size', title: '文件大小', sort: true}
        , {field: 'createTime', title: '操作时间', width: '10%',templet: '<div>{{ layui.laytpl.toDateString(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>'}
        , {field: 'text', title: '操作', width: '10%', toolbar:'#toolBar'}

      ]]
      , page: true
      ,  height: 'full-105'
    });

    var $ = layui.$, active = {
      select: function () {
        var userName = $('#userName').val();
        var type = $('#type').val();
        table.reload('logfile', {
          where: {
              userName: userName,
              type: type
          }
        });
      }
      ,look:function(){
          var checkStatus = table.checkStatus('logfile')
                  , data = checkStatus.data;
          if (data.length ==0) {
              layer.msg('请选择要删除的数据', {icon: 5});
              return false;
          }
          var ids=[];
          for(item in data){
              ids.push(data[item].id);
          }
          look(ids);
        }
      ,reload:function(){
        $('#userName').val('');
       $('#type').val('');
        table.reload('logfile', {
          where: {
              userName: null,
              type: null
          }
        });
      },
    };
    //监听工具条
    table.on('tool(log)', function (obj) {
      var data = obj.data;
      if (obj.event === 'look') {
      layer.prompt({title: '查看文件前几行，并确认', formType: 0}, function(pass, index){
        look('查看文件', '/error/readLog?lastNLine=' + pass, 700, 550)
        layer.close(index);
      });
      }
    });

    $('.layui-col-md12 .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    $('.select .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });

  });

  function look(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    if (url == null || url == '') {
      url = "404.html";
    }
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    layer.open({
      id: 'log-look',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: false,
      shade: 0.4,
      title: title,
      content: url
    });
  }
</script>
</body>

</html>
