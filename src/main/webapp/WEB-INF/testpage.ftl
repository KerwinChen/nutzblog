<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>Auto height - Editor.md examples</title>
    <link rel="stylesheet" href="/adm/assets/js/editor.md/css/style.css"/>
    <link rel="stylesheet" href="/adm/assets/js/editor.md/css/editormd.css"/>

    <script src="/adm/assets/js/jquery-1.11.3.min.js"></script>
    <script src="/adm/assets/js/editor.md/editormd.min.js"></script>

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

<script type="text/javascript">
    var testEditor;

    $(function () {
        testEditor = editormd("test-editormd", {
            width: "100%",
//            height: 300,
            toolbarAutoFixed: false,
            autoFocus: false,
             autoHeight: true,
            // watch: false,             //实时预览
//            syncScrolling: "single",
//            saveHTMLToTextarea: true,
            path: "/adm/assets/js/editor.md/lib/",
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/adm/upload_editor",
            htmlDecode: true,
            toolbarIcons: function () {
                return [
                    "undo", "redo", "|",
                    "bold", "del", "italic", "quote", "|",
                    "h2", "h3", "h4", "|",
                    "list-ul", "list-ol", "hr", "|",
                    "watch", "preview", "fullscreen", "|",
                    "link", "image", "code-block", "table", "datetime", "html-entities",
                    "help", "info"
                ];
            }

        });

        $("#append-btn").click(function () {
            var md = "[![](https://pandao.github.io/editor.md/examples/images/7.jpg)](https://pandao.github.io/editor.md/images/7.jpg \"李健首张专辑《似水流年》封面\")";
            testEditor.appendMarkdown(md);

//            $.get("./test.md", function(md){
//                testEditor.appendMarkdown(md);
//            });
        });


    });
</script>
</body>
</html>