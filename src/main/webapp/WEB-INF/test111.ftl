
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>Auto height - Editor.md examples</title>
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="../css/editormd.css" />
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon" />
</head>
<body>
<div id="layout">
    <header>
        <h1>Auto height test</h1>
    </header>
    <div class="btns">
        <button id="append-btn">Append markdown</button>
    </div>
    <div id="test-editormd">
                <textarea style="display:none;">### Settings

```javascript
var testEditor = editormd("test-editormd", {
    autoHeight : true
});
```
</textarea>
    </div>
</div>
<link rel="stylesheet" href="/adm/assets/js/editor.md/css/editormd.css"/>
<script src="/adm/assets/js/editor.md/editormd.min.js"></script>

<script type="text/javascript">
    var testEditor;

    $(function() {
        testEditor = editormd("test-editormd", {
            width           : "90%",
            autoHeight      : true,
            path: "/adm/assets/js/editor.md/lib/",
            htmlDecode      : "style,script,iframe",
            tex             : true,
            emoji           : true,
            taskList        : true,
            flowChart       : true,
            sequenceDiagram : true
        });

        $("#append-btn").click(function(){
            $.get("./test.md", function(md){
                testEditor.appendMarkdown(md);
            });
        });
    });


</script>
</body>
</html>