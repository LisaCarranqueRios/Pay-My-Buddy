    $(document).ready(function () {
        $(document).ready(function() {
            $('#sort').dataTable( {
                "ordering": false,
                "info":     false,
                "searching": false,
                "bLengthChange": false,
                "displayLength": 3,
                  "language": {
                    "paginate": {
                      "previous": "<<",
                      "next": ">>"
                    }
                  }
            } );
        } );
        $('#selectid').prop('disabled', true);
        $('#selectid').prop('selected', true);
        $.fn.DataTable.ext.pager.numbers_length = 5;
    });