var app = angular.module("myApp", [])
    .controller('GeneralController', function ($scope, $http) {
        
        $scope.isstarted;
        _refreshIsStarted();

        function _refreshIsStarted() {
            $http({
                method : 'GET',
                url : 'props/getisstarted'
            }).then(
                (response) =>   {
                    $scope.isstarted = response.data;
                    setButtonsIsEnabledOrDisabled($scope.isstarted);
                                },
                (e) => {console.log(response.statusText);}
            );
        }

        function setButtonsIsEnabledOrDisabled(b) {
            if(b) {
                $(function() {
                    $("#btnstarted").prop("disabled", true);
                    $("#btnstopped").removeAttr('disabled');
                });
            }
            else {
                $(function() {
                    $("#btnstarted").removeAttr('disabled');
                    $("#btnstopped").prop("disabled", true);
                });
            }
        }

        $scope.setStarted = function() {
            $scope.isstarted=true;
            httpPutIsStarted(true);
            setButtonsIsEnabledOrDisabled(true);

        }

        $scope.setStopped = function() {
            $scope.isstarted=false;
            httpPutIsStarted(false);
            setButtonsIsEnabledOrDisabled(false);
        }

        function httpPutIsStarted(isstarted) {
            $http({
                method : 'PUT',
                url : 'props/setisstarted/' + isstarted,
                data: {"isstarted" : isstarted}
            }).then((response) => {return response.data;},
            (e) => {console.log(e);}
            );
        }

});