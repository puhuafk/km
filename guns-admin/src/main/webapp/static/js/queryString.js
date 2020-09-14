/**
 * Created by helang on 2018/8/5.
 * Email:helang.love@qq.com
 */

$.extend({
    queryString:{
        /* 获取
         * key:需要获取的对象，默认返回地址栏所有对象
         * */
        get:function (key) {
            var search=window.location.search;
            if(search.length<1){
                return;
            }
            if(key && search.indexOf(key)<0){
                return;
            }
            search=search.substring(1);
            search=decodeURI(search);
            var searchArr=search.split("&");
            var searchJSON={};
            $.each(searchArr,function (index,item) {
                var item=item.split("=");
                searchJSON[item[0]]=item[1];
            });
            if(key){
                return searchJSON[key];
            }else {
                return searchJSON;
            }
        },
        /* 设置
         * data:数值对象，url:跳转的路径(默认为当前路径),isOpen:是否新建窗口打开(默认为否)
         * 如果传递的为空对象，即删除所有参数
         * */
        set:function (data,url,isOpen) {
            if(!data || $.type(data)!='object'){
                return;
            }
            var pathname=url || window.location.pathname;
            var str="?",index=0;
            if($.isEmptyObject(data)){
                str="";
            }else {
                $.each(data,function (key,val) {
                    str+=(index>0 ? "&" : "")+key+"="+val;
                    index++;
                });
            }
            if(isOpen){
                window.open(pathname+str);
            }else {
                window.location.href = pathname+str;
            }

        },
        /* 添加
         * data:数值对象，url:跳转的路径(默认为当前路径),isOpen:是否新建窗口打开(默认为否)
         * 如果当前某项值已经存在地址栏中，将更新该值，不存在则添加值
         * */
        add:function (data,url,isOpen) {
            if(!data || $.type(data)!='object'){
                return;
            }
            var query=this.get();
            if(query){
                $.each(query,function (key,val) {
                    if(!data.hasOwnProperty(key)){
                        data[key]=val;
                    }
                });
            }
            this.set(data,url,isOpen);
        },
        /* 更新
         * data:数值对象，url:跳转的路径(默认为当前路径),isOpen:是否新建窗口打开(默认为否)
         * 仅更新当前地址栏已经存在的对象值
         * */
        update:function (data,url,isOpen) {
            if(!data || $.type(data)!='object'){
                return;
            }
            var query=this.get();
            if(query){
                $.each(data,function (key,val) {
                    if(query.hasOwnProperty(key)){
                        query[key]=val;
                    }
                });
            }
            this.set(query,url,isOpen);
        },
        /* 删除
         * data:数值数组，url:跳转的路径(默认为当前路径),isOpen:是否新建窗口打开(默认为否)
         * 如果传递的为空数组，即删除所有参数
         * */
        delete:function (data,url,isOpen) {
            if(!data || $.type(data)!='array'){
                return;
            }
            var query=this.get();
            if(data.length==0){
                query={};
            }else {
                $.each(data,function (index,item) {
                    if(query.hasOwnProperty(item)){
                        delete query[item];
                    }
                });
            }
            this.set(query,url,isOpen);
        }
    }
});
