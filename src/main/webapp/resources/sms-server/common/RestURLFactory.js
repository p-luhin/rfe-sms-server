angular.module('sms-server').factory('RestURLFactory',
    function () {
        var BASE_REST_PREFIX = '/rest';
        return {
            SEND_CUSTOM_SMS: BASE_REST_PREFIX + '/sms/send/custom',
            SEND_BULK_SMS: BASE_REST_PREFIX + '/sms/send/bulk',
            GET_FULL_STATISTICS: BASE_REST_PREFIX + '/statistics',
            PERSONS: BASE_REST_PREFIX + '/recipient/persons',
            ALL_PERSONS: BASE_REST_PREFIX + '/recipient/persons/all',
            PERSONS_WITH_GROUP: BASE_REST_PREFIX + "/recipient/persons/withGroup/",
            PERSONS_WITHOUT_GROUP: BASE_REST_PREFIX + "/recipient/persons/withoutGroup/",
            GROUP: BASE_REST_PREFIX + '/recipient/group',
            GET_ALL_RECIPIENTS: BASE_REST_PREFIX + '/recipient/all',
            LOGOUT: BASE_REST_PREFIX + '/user/logout',
            AUTHENTICATE: BASE_REST_PREFIX + '/user/authenticate',
            GET_SENDER_NAMES: BASE_REST_PREFIX + '/user/senderNames',
            CREDENTIALS: BASE_REST_PREFIX + "/setup/credentials",
            SMS_TEMPLATE: BASE_REST_PREFIX + "/setup/smsTemplate",
            FIND_TEMPLATE: BASE_REST_PREFIX + "/sms/template",
            USERS: BASE_REST_PREFIX + "/setup/user",
            APPLICATION: BASE_REST_PREFIX + "/setup/application",
            EMAIL_TEMPLATE: BASE_REST_PREFIX + "/setup/emailTemplate",
            SHARE_CREDENTIALS: BASE_REST_PREFIX + "/setup/shareCredentials",
            CHANGE_PASSWORD: BASE_REST_PREFIX + "/setup/changePassword",
            VERSION: BASE_REST_PREFIX + "/setup/version",
            AVAILABLE_SMS_TEMPLATES: BASE_REST_PREFIX + "/setup/emailTemplate/smsTypes",
            SMS_SERVER_PROPERTIES: BASE_REST_PREFIX + "/setup/properties",
            SMS_QUEUE: BASE_REST_PREFIX + "/sms/queue"
        }
    }
);