<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html class="no-js">
    <head>
        <meta charset="utf-8">
        <title>Instagram auth callback</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <link rel="shortcut icon" href="/favicon.ico">

    </head>
    <body>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <script src="//10cms.qa.s3.amazonaws.com/benp/ugcviewer/libs.min.js"></script>
    <script src="//10cms.qa.s3.amazonaws.com/benp/ugcviewer/viewer.min.js"></script>
    <script>
        var instagram = new amp.Instagram();
        instagram.initCallback();
    </script>
    </body>
</html>
