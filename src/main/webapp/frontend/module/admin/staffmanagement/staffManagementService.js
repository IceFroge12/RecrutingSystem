/**
 * Created by dima on 30.04.16.
 */
'use strict';

function staffManagementService(http) {

    var service = {};

    service.showAllEmployees = function (pageNum, rowsNum, sortingCol, increase) {
        console.log("Service showAllEmployees");
        return http({
            method : 'GET',
            url : '/admin/showAllEmployees',
            params : {pageNum:pageNum, rowsNum: rowsNum, sortingCol:sortingCol, increase: increase}
        })
    };

    service.showFilteredEmployees = function (pageNum, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer,
                                         notInterviewer, notEvaluated) {
        console.log("Service showFilteredEmployees");
        return http({
            method: 'GET',
            url: '/admin/showFilteredEmployees',
            params: {
                pageNum: pageNum,
                rowsNum: rowsNum,
                sortingCol: sortingCol,
                increase: increase,
                idStart: idStart,
                idFinish: idFinish,
                rolesId: roles,
                interviewer: interviewer,
                notInterviewer: notInterviewer,
                notEvaluated: notEvaluated
            }
        })
    };



    service.getCountOfEmployee = function () {
        return http({
            method : 'GET',
            url : '/admin/getCountOfEmployee'
        })
    };

    service.searchEmployee = function (lastName) {
        return http({
            method : 'POST',
            url : '/admin/search',
            params : {lastName : lastName}
        })
    };


    service.getEmployeeRoles = function (id) {
        return http({
            method : 'GET',
            url : '/admin/getRoles',
            params:{id:id}
        })
    };

    
    service.addEmployee = function (firstName, secondName, lastName, email, roles) {
        console.log(firstName+secondName+ lastName+email+roles);
        http({
            method : 'POST',
            url : '/admin/addEmployee',
            contentType: 'application/json',
            data : JSON.stringify({
                firstName: firstName,
                secondName: secondName,
                lastName: lastName,
                email: email,
                roleList: roles
            })
        })
    };


    service.editEmployee = function (id,firstName, secondName, lastName, email, roles) {
        console.log(roles+"ROLES");
        http({
            method : 'POST',
            url : '/admin/editEmployee',
            contentType: 'application/json',
            data : {
                id:id,
                firstName: firstName,
                secondName: secondName,
                lastName: lastName,
                email: email,
                roleList: roles
            }
        });
    };


    service.changeEmployeeStatus = function (email) {
        console.log(email)
        http({
            method : 'GET',
            url : '/admin/changeEmployeeStatus',
            params : {email:email}
        }).success(function (data, status, headers) {
            console.log(data);
                return data;
        });
    };

    service.showAssigned = function (email) {
        http({
            method : 'POST',
            url : '/admin/showAssignedStudent',
            params : {email:email}
        }).success(function (data, status, headers) {
            return data;
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };


    service.deleteEmployee = function (email) {
        console.log(email);
        http({
            method : 'GET',
            url:'/admin/deleteEmployee',
            params:{email:email}
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };

    service.getMaxId = function () {
        return http({
            method : 'GET',
            url : '/admin/getMaxId'
        })
    };

    service.deleteAssignedStudent = function (employee) {
        console.log('deleteAssignedStudentService');
        console.log(employee);
        http({
            method : 'POST',
            url:'/admin/deleteAssignedStudent',
            contentType: 'application/json',
            params:{
                idInterview:employee.idInterview
            }
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };

    return service;
}



angular.module('appStaffManagement')
    .service('staffManagementService', ['$http', staffManagementService]);