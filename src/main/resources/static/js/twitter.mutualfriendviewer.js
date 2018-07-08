$(document).ready(function () {
    $( "#mutual-friend-finder" ).submit(function( event ) {
    event.preventDefault();
   postScreenNames();
    });
});

function postScreenNames() {

    $("#btn-search").prop("disabled", true);

    var form = new FormData();
    form.append("screenName1", $("#first").val());
    form.append("screenName2", $("#second").val());

    $.ajax({
      "url": "/service/findmutualfriends",
      "method": "POST",
      "headers": {
        "cache-control": "no-cache",
      },
      "processData": false,
      "contentType": false,
      "mimeType": "multipart/form-data",
      "data": form,
        "timeout": 600000,
        "success": function (data) {

            var data = JSON.parse(data);
            var dataSet =[];
            for (var i = 0; i < data.length; i++) {
                dataSet.push([data[i]])
              }
             console.log("SUCCESS : ", data);

            drawTable(dataSet);
            $("#btn-search").prop("disabled", false);
        },
        "error": function (e) {
            var errorModal = $("#errorModal");
            errorModal.find(".modal-body p").text(e.responseText);
            errorModal.modal('show');

              var dataSet =[];
            drawTable(dataSet);
            $("#btn-search").prop("disabled", false);
        }
    });

    function drawTable(dataSet){
     if ( $.fn.DataTable.isDataTable('#mutual-friends-table') ) {
                              $('#mutual-friends-table').DataTable().destroy();
                            }
       $('#mutual-friends-table').DataTable( {
                                        data: dataSet,
                                                columns: [
                                                    { title: "Mutual Friend" }
                                                ],
                                                "language": {
                                                    "emptyTable": "No Mutual Friends"
                                                  }
                                    } );
    }

}