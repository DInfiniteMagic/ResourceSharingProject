<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--文件上传div-->
<div class="row col-sm-offset-2">
    <div class="col-sm-10">
        <h3 style="border-bottom: red solid 2px">资源上传</h3>
    </div>
</div>
<form  class="form-horizontal" enctype="multipart/form-data" id="fileInformationForm" method="post"><!--文件信息-->
    <div class="form-group">
        <div class="col-sm-3"></div>
        <div class="col-sm-6" id="fileDiv" style="height: 50px">
                    <span id="uploadSpan">
                        <svg  id="addSvg" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594646589766" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="2017" width="32" height="32"><defs><style type="text/css"/></defs><path d="M937.12896 893.51168c-2.78528 55.19872-70.66112 43.33056-108.40064 43.33056H181.12c-35.74784 0-83.62496 4.33664-83.62496-46.976v-128.70144-476.09856-138.11712c0-49.13664 40.20736-49.75616 76.53376-49.75616H819.37408c34.36032 0 92.06784-10.94144 112.93184 23.3984 6.81472 11.21792 4.82304 28.07808 4.82304 40.83712V893.51168c0 33.01376 51.2 33.01376 51.2 0V159.63648c0-15.89248 0.0256-30.70464-4.01408-46.40768-10.53696-40.97536-52.06528-67.23072-92.73344-67.23072H149.51936c-61.05088 0-103.2192 42.16832-103.2192 103.2192v742.07744c0 43.8528 26.88512 77.97248 67.23072 92.73344 14.34624 5.25312 31.24736 4.01408 46.40768 4.01408H893.7984c53.30944 0 91.93984-43.24864 94.53056-94.53056 1.664-33.01888-49.54112-32.85504-51.2 0z" p-id="2018"/><path d="M491.71456 230.29248v573.44c0 33.01376 51.19488 33.01376 51.19488 0v-573.44c0-33.01376-51.19488-33.01376-51.19488 0z" p-id="2019"/><path d="M230.59456 542.61248h573.43488c33.01376 0 33.01376-51.2 0-51.2H230.59456c-33.01888 0-33.01888 51.2 0 51.2z" p-id="2020" /></svg>
                        <svg  id="fileSvg" style="display: block" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594649770729" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="3427" width="32" height="32"><defs><style type="text/css"/></defs><path d="M186.181818 837.818182l325.818182 0 0 46.545455-325.818182 0 0-46.545455ZM837.818182 837.818182 837.818182 884.363636 930.909091 884.363636 930.909091 0 232.727273 0 232.727273 93.090909 279.272727 93.090909 279.272727 46.545455 884.363636 46.545455 884.363636 837.818182ZM186.181818 698.181818l512 0 0 46.545455-512 0 0-46.545455ZM93.090909 149.410909l0 864.814545C93.090909 1019.624727 97.373091 1024 102.679273 1024l678.958545 0C786.990545 1024 791.272727 1019.671273 791.272727 1014.225455L791.272727 149.410909C791.272727 144.011636 786.990545 139.636364 781.684364 139.636364L102.679273 139.636364C97.373091 139.636364 93.090909 143.965091 93.090909 149.410909zM139.636364 186.181818l605.090909 0 0 791.272727L139.636364 977.454545 139.636364 186.181818zM186.181818 279.272727l512 0 0 46.545455-512 0 0-46.545455ZM186.181818 418.909091l512 0 0 46.545455-512 0 0-46.545455ZM186.181818 558.545455l512 0 0 46.545455-512 0 0-46.545455Z" p-id="3428"/></svg>
                        <svg  id="errorSvg" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" t="1594661371016" class="icon" viewBox="0 0 1024 1024" version="1.1" p-id="2031" width="32" height="32"><defs><style type="text/css"/></defs><path d="M512 1024C229.248 1024 0 794.752 0 512S229.248 0 512 0s512 229.248 512 512-229.248 512-512 512z m0-938.666667C276.352 85.333333 85.333333 276.352 85.333333 512s191.018667 426.666667 426.666667 426.666667 426.666667-191.018667 426.666667-426.666667S747.648 85.333333 512 85.333333z m198.698667 625.365334a42.666667 42.666667 0 0 1-60.330667 0L512 572.330667l-138.368 138.368a42.666667 42.666667 0 0 1-60.330667-60.330667L451.669333 512 313.301333 373.632a42.666667 42.666667 0 0 1 60.330667-60.330667L512 451.669333l138.368-138.368a42.624 42.624 0 1 1 60.330667 60.330667L572.330667 512l138.368 138.368a42.666667 42.666667 0 0 1 0 60.330667z" p-id="2032" fill="#d81e06"/></svg>
                    </span>
            <a id="fileNameA">点击此处上传文件</a>
            <a id="renewUpload"></a>
            <input type="file" name="fileUpload"  id="fileUpload" onchange="change()">
        </div>
    </div>
    <div class="form-group" id="progressDiv" style="display: none">
        <div class="col-sm-3"></div>
        <div class="col-sm-6" style="height: 20px ">
            <div class="progress">
                <div id="showProgress" class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                    0
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="resourceName" class="col-sm-3 control-label" style="padding-right: 0px">资源名称:</label>
        <div class="col-sm-6">
            <input type="text"  name="resourceName" class="form-control" id="resourceName" placeholder="请输入名称">
        </div>
    </div>
    <div class="form-group">
        <label for="resourceClass" class="col-sm-3 control-label" style="padding-right: 0px">所属分类:</label>
        <div class="col-sm-6">
            <!--<input type="text"  >-->
            <select name="resourceClass" class="form-control" id="resourceClass" >
<%--
                <!--此处的值需要后端传来-->
                <option>信息科学</option>
                <option>应用数学</option>
                <option>安全计数</option>
                <option>其他</option>--%>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="resourceValue" class="col-sm-3 control-label" style="padding-right: 0px">所需积分:</label>
        <div class="col-sm-6">
            <select name="resourceValue" class="form-control" id="resourceValue" placeholder="" >
                <!--此处的值需要后端传来-->
                <option>0</option>
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="resourceDescription" class="col-sm-3 control-label" style="padding-right: 0px">资源描述:</label>
        <div class="col-sm-6" id="resourceDescriptionDiv">
            <textarea rows="8" style="max-width: 100%;min-height: 30%;min-width: 100%" class="col-sm-12" name="resourceDescription" id="resourceDescription" maxlength="500" placeholder="详细的文件描述，能更好的方便他人下载"></textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-6">
            <button type="button" id="ToSubmit" name="ToSubmit" class="btn btn-danger btn-lg">提&nbsp;&nbsp;&nbsp;&nbsp;交</button>
        </div>
    </div>
</form>
<script src="${ctxStatics}/load/upload/js/uploadContext.js"></script>