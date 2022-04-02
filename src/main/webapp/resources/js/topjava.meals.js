const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
                $.ajax({
                        type: "GET",
                        url: mealAjaxUrl + "filter",
                        data: $("#filter").serialize()
                }).done(filterWithData);
            },
};

function cancelFilter() {
        $("#filter")[0].reset();
        $.get(mealAjaxUrl, filterWithData);
    }



$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "datetime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});