$(function(){
    $.ajaxSetup({
        contentType: "application/json; charset=utf-8"
    });

    $.getJSON("/api/user", function(data){
        var table = $("#users");

        data.forEach(function(user){
            $("<tr><td>"+user.username+"</td><td>"+user.password+"</td></tr>").appendTo(table);
        });    
    }); 

    $("#save").click(function(){
        var table = $("#users");

        var payload = JSON.stringify({username: $("#username").val(), password: $("#password").val()});
        $.post("/api/user", payload, function(user){
            $("<tr><td>"+user.username+"</td><td>"+user.password+"</td></tr>").appendTo(table);
        });
    });
});
