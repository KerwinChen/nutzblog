alert("加载成功！");

//require.config({paths: {"jquery": "http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min"}});

require(['math'], function (math) {
    alert(math.add(1, 1));
});




