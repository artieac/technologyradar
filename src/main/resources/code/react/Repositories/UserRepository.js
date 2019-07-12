export class UserRepository {
    getUser(successHandler) {
        var getUrl = '/api/User';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (currentUser) {
                    successHandler(currentUser);
                }.bind(this)
            });
    }

     getAll(successHandler) {
        var getUrl = '/api/Users';

        jQuery.ajax({
                url: getUrl,
                async: true,
                dataType: 'json',
                success: function (users) {
                    successHandler(users);
                }.bind(this)
            });
        }
}
