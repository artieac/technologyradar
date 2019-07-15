export class RoleRepository {
     getAll(successHandler) {
        var getUrl = '/api/roles';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (roles) {
                    successHandler(roles);
                }.bind(this)
            });
        }
}