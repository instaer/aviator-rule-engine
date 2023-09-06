"use strict";(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[844],{1849:function(e,t,n){var r=n(4836);t.Z=void 0;var a=r(n(4938)),l=n(5893),o=(0,a.default)((0,l.jsx)("path",{d:"M17.65 6.35C16.2 4.9 14.21 4 12 4c-4.42 0-7.99 3.58-7.99 8s3.57 8 7.99 8c3.73 0 6.84-2.55 7.73-6h-2.08c-.82 2.33-3.04 4-5.65 4-3.31 0-6-2.69-6-6s2.69-6 6-6c1.66 0 3.14.69 4.22 1.78L13 11h7V4l-2.35 2.35z"}),"Refresh");t.Z=o},1844:function(e,t,n){n.r(t),n.d(t,{default:function(){return $}});var r=n(17),a=n(29),l=n(9499),o=n(7794),i=n.n(o),s=n(9410),u=n(2176),d=n(1404),c=n(7294),m=n(6886),f=n(1903),h=n(657),x=n(4808),g=n(8456),p=n(7645),v=n(6580),j=n(6901),Z=n(1425),b=n(3321),C=n(3946),k=n(2761),P=n(3343),y=n(3205),w=n(1733),S=n(7957),D=n(7036),R=n(594),O=n(1849),z=n(9734),L=n(8207),M=n(6300),V=n(5529),I=n(7385),N=n(5893);function T(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),n.push.apply(n,r)}return n}function E(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?T(Object(n),!0).forEach(function(t){(0,l.Z)(e,t,n[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):T(Object(n)).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))})}return e}function B(e){var t=e.searchParam,n=e.setSearchParam,r=e.handleSearch;return(0,N.jsxs)(N.Fragment,{children:[(0,N.jsx)(m.ZP,{item:!0,xs:3,children:(0,N.jsx)(f.Z,{label:"规则集编码",value:t.code,onChange:function(e){return n(E(E({},t),{},{code:(0,I.f)(e.target.value)}))},InputProps:{autoComplete:"off",maxLength:20,endAdornment:(0,N.jsx)(C.Z,{onClick:function(){return n(E(E({},t),{},{code:""}))},children:(0,N.jsx)(P.Z,{})})},size:"small"})}),(0,N.jsx)(m.ZP,{item:!0,xs:2,children:(0,N.jsx)(C.Z,{onClick:function(){return r()},children:(0,N.jsx)(k.Z,{})})})]})}function W(e){var t,n,r,a=e.data,o=e.error,i=e.isLoading,f=e.paginationModel,h=e.setPaginationModel,x=e.mutate,g=(0,c.useState)([]),p=g[0],v=g[1],j=(0,c.useState)((null==a?void 0:null===(t=a.body)||void 0===t?void 0:t.totalElements)||0),Z=j[0],b=j[1];(0,c.useEffect)(function(){b(function(e){var t,n;return(null==a?void 0:null===(t=a.body)||void 0===t?void 0:t.totalElements)!==void 0?null==a?void 0:null===(n=a.body)||void 0===n?void 0:n.totalElements:e})},[null==a?void 0:null===(n=a.body)||void 0===n?void 0:n.totalElements,b]);var k=(null==a?void 0:null===(r=a.body)||void 0===r?void 0:r.content)||[],P=[E({},s.n),{field:"id",headerName:"ID",width:50,sortable:!1},{field:"code",headerName:"规则集编码",width:200,sortable:!1},{field:"name",headerName:"规则集名称",width:200,sortable:!1},{field:"defaultReturnValues",headerName:"默认返回值集合",width:200,sortable:!1},{field:"mode",headerName:"模式",sortable:!1,width:70,valueGetter:function(e){var t=e.row.mode;return 1===t?"已创建":0===t?"创建中":""}},{field:"remark",headerName:"备注说明",width:200,sortable:!1}];P.forEach(function(e){e.renderHeader=function(){return(0,N.jsx)("strong",{children:e.headerName})}});var D=k.find(function(e){return e.id===parseInt(p.toString())}),R=(0,c.useState)({add:!1,edit:!1,remove:!1,refresh:!1}),z=R[0],L=R[1],M=function(e){L(function(t){return E(E({},t),{},(0,l.Z)({},e,"add"===e||!!D))})},V=function(e,t,n){"backdropClick"!==t&&L(function(e){return E(E({},e),{},(0,l.Z)({},n,!1))})};return(0,N.jsxs)(N.Fragment,{children:[(0,N.jsx)(m.ZP,{item:!0,xs:12,children:(0,N.jsx)(d._,{autoHeight:!0,rows:k,columns:P,rowCount:Z,loading:i,pageSizeOptions:[5,10],paginationMode:"server",paginationModel:f,onPaginationModelChange:h,checkboxSelection:!0,disableMultipleRowSelection:!0,disableColumnMenu:!0,rowSelectionModel:p,onRowSelectionModelChange:function(e,t){v(function(t){return e.filter(function(e){return!t.includes(e)})})},localeText:{noRowsLabel:o?"Failed to load. Caused by: ".concat(o):"No Rows"},slots:{toolbar:function(){return(0,N.jsxs)(u.D,{children:[(0,N.jsx)(C.Z,{"aria-label":"add",color:"primary",sx:{marginBottom:"10px"},onClick:function(){return M("add")},children:(0,N.jsx)(y.Z,{})}),(0,N.jsx)(C.Z,{"aria-label":"edit",color:"primary",sx:{marginBottom:"10px"},onClick:function(){return M("edit")},children:(0,N.jsx)(S.Z,{})}),(0,N.jsx)(C.Z,{"aria-label":"remove",color:"primary",sx:{marginBottom:"10px"},onClick:function(){return M("remove")},children:(0,N.jsx)(w.Z,{})}),(0,N.jsx)(C.Z,{"aria-label":"refresh",color:"primary",sx:{marginBottom:"10px"},onClick:function(){return M("refresh")},children:(0,N.jsx)(O.Z,{})})]})}},sx:{"& .MuiDataGrid-cell":{bgcolor:function(e){return"dark"===e.palette.mode?"#376331":"rgb(255,215,115, 0.19)"}}}})}),(0,N.jsx)(A,{openDialog:z.add,handleCloseDialog:function(e,t){return V(e,t,"add")},mutate:x}),D&&(0,N.jsx)(_,{selectedRow:D,openDialog:z.edit,handleCloseDialog:function(e,t){return V(e,t,"edit")},mutate:x}),D&&(0,N.jsx)(q,{selectedRow:D,openDialog:z.remove,handleCloseDialog:function(e,t){return V(e,t,"remove")},mutate:x}),D&&(0,N.jsx)(F,{selectedRow:D,openDialog:z.refresh,handleCloseDialog:function(e,t){return V(e,t,"refresh")},mutate:x})]})}function A(e){var t,n=e.openDialog,r=e.handleCloseDialog,l=e.mutate,o={code:"",name:"",remark:"",defaultReturnValues:""},s={message:"操作成功",alertType:"success",openAlert:!1,loading:!1,fieldMessage:{}},u=(0,c.useState)(o),d=u[0],k=u[1],P=(0,c.useState)(s.message),y=P[0],w=P[1],S=(0,c.useState)(s.alertType),O=S[0],z=S[1],V=(0,c.useState)(s.openAlert),T=V[0],B=V[1],W=(0,c.useState)(s.loading),A=W[0],_=W[1],q=(0,c.useState)(s.fieldMessage),F=q[0],G=q[1],H={code:{required:{value:!0,message:"必填字段"},maxLength:{value:20,message:"最大长度为20"},regex:{value:/^[A-Z0-9_\-#]+$/,message:"仅能包含英文（大写）、数字、下划线、短横线、#等字符"}},name:{required:{value:!0,message:"必填字段"},maxLength:{value:50,message:"最大长度为50"}},remark:{maxLength:{value:128,message:"最大长度为128"}},defaultReturnValues:{maxLength:{value:256,message:"最大长度为256"}}};(0,c.useEffect)(function(){n||(k(o),w(s.message),z(s.alertType),B(s.openAlert),G(s.fieldMessage))},[n]);var $=function(){G((0,M.N)(d,H))},J=function(){B(!1)},K=(t=(0,a.Z)(i().mark(function e(){var t,n;return i().wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(J(),!((t=(0,M.N)(d,H))&&Object.keys(t).length>0)){e.next=5;break}return G(t),e.abrupt("return");case 5:return _(!0),e.next=8,(0,L.D2)("admin/rulesetInfo/save",d);case 8:n=e.sent.error,_(!1),n?(w(n.message),z("error"),B(!0)):(r(),l(void 0));case 12:case"end":return e.stop()}},e)})),function(){return t.apply(this,arguments)});return(0,N.jsxs)(h.Z,{open:n,onClose:r,children:[(0,N.jsx)(x.Z,{open:A,invisible:!0,sx:{zIndex:function(e){return e.zIndex.drawer+1}},children:(0,N.jsx)(g.Z,{variant:"indeterminate",disableShrink:!1,sx:{color:function(e){return"light"===e.palette.mode?"#1a90ff":"#308fe8"}},size:50,thickness:4})}),(0,N.jsxs)(p.Z,{children:["新增规则集",(0,N.jsx)(C.Z,{"aria-label":"close",onClick:r,sx:{position:"absolute",right:8,top:8,color:function(e){return e.palette.grey[500]}},children:(0,N.jsx)(R.Z,{})})]}),(0,N.jsx)(v.Z,{dividers:!0,children:(0,N.jsxs)(m.ZP,{container:!0,spacing:2,children:[(0,N.jsx)(m.ZP,{item:!0,xs:6,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"add-ruleset-code",label:"规则集编码",size:"small",fullWidth:!0,variant:"outlined",value:d.code,onChange:function(e){return k(E(E({},d),{},{code:(0,I.f)(e.target.value)}))},inputProps:{maxLength:20},error:!!(null!=F&&F.code),helperText:null==F?void 0:F.code,onBlur:$})}),(0,N.jsx)(m.ZP,{item:!0,xs:6,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"add-ruleset-name",label:"规则集名称",size:"small",fullWidth:!0,variant:"outlined",value:d.name,onChange:function(e){return k(E(E({},d),{},{name:(0,I.f)(e.target.value)}))},inputProps:{maxLength:50},error:!!(null!=F&&F.name),helperText:null==F?void 0:F.name,onBlur:$})}),(0,N.jsx)(m.ZP,{item:!0,xs:9,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"add-ruleset-defaultReturnValues",label:"默认返回值集合",size:"small",fullWidth:!0,variant:"outlined",value:d.defaultReturnValues,onChange:function(e){return k(E(E({},d),{},{defaultReturnValues:(0,I.f)(e.target.value)}))},inputProps:{maxLength:256},error:!!(null!=F&&F.defaultReturnValues),helperText:null==F?void 0:F.defaultReturnValues,onBlur:$})}),(0,N.jsx)(m.ZP,{item:!0,xs:9,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"add-ruleset-remark",label:"规则集备注",size:"small",fullWidth:!0,variant:"outlined",value:d.remark,onChange:function(e){return k(E(E({},d),{},{remark:(0,I.f)(e.target.value)}))},inputProps:{maxLength:1024},error:!!(null!=F&&F.remark),helperText:null==F?void 0:F.remark,onBlur:$})}),T&&(0,N.jsx)(m.ZP,{item:!0,xs:12,children:(0,N.jsx)(j.Z,{severity:O,onClose:J,children:y})})]})}),(0,N.jsx)(Z.Z,{children:(0,N.jsx)(b.Z,{"aria-label":"addSave",onClick:K,variant:"contained",color:"primary",startIcon:(0,N.jsx)(D.Z,{}),disabled:A,children:"保存"})})]})}function _(e){var t,n=e.selectedRow,r=e.openDialog,l=e.handleCloseDialog,o=e.mutate,s={id:n.id,code:n.code,name:n.name,remark:n.remark||"",defaultReturnValues:n.defaultReturnValues||"",expression:n.expression||""},u={message:"操作成功",alertType:"success",openAlert:!1,loading:!1,fieldMessage:{}},d=(0,c.useState)(s),k=d[0],P=d[1],y=(0,c.useState)(u.message),w=y[0],S=y[1],O=(0,c.useState)(u.alertType),z=O[0],V=O[1],T=(0,c.useState)(u.openAlert),B=T[0],W=T[1],A=(0,c.useState)(u.loading),_=A[0],q=A[1],F=(0,c.useState)(u.fieldMessage),G=F[0],H=F[1],$={name:{required:{value:!0,message:"必填字段"},maxLength:{value:50,message:"最大长度为50"}},remark:{maxLength:{value:128,message:"最大长度为128"}},defaultReturnValues:{maxLength:{value:256,message:"最大长度为256"}}};(0,c.useEffect)(function(){r||(P(s),S(u.message),V(u.alertType),W(u.openAlert),H(u.fieldMessage))},[r]);var J=function(){H((0,M.N)(k,$))},K=function(){W(!1)},Q=(t=(0,a.Z)(i().mark(function e(){var t,n;return i().wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(K(),!((t=(0,M.N)(k,$))&&Object.keys(t).length>0)){e.next=5;break}return H(t),e.abrupt("return");case 5:return q(!0),e.next=8,(0,L.D2)("admin/rulesetInfo/save",k);case 8:n=e.sent.error,q(!1),n?(S(n.message),V("error"),W(!0)):(l(),o(void 0));case 12:case"end":return e.stop()}},e)})),function(){return t.apply(this,arguments)});return(0,N.jsxs)(h.Z,{open:r,onClose:l,children:[(0,N.jsx)(x.Z,{open:_,invisible:!0,sx:{zIndex:function(e){return e.zIndex.drawer+1}},children:(0,N.jsx)(g.Z,{variant:"indeterminate",disableShrink:!1,sx:{color:function(e){return"light"===e.palette.mode?"#1a90ff":"#308fe8"},animationDuration:"550ms"},size:50,thickness:4})}),(0,N.jsxs)(p.Z,{children:["修改规则集",(0,N.jsx)(C.Z,{"aria-label":"close",onClick:l,sx:{position:"absolute",right:8,top:8,color:function(e){return e.palette.grey[500]}},children:(0,N.jsx)(R.Z,{})})]}),(0,N.jsx)(v.Z,{dividers:!0,children:(0,N.jsxs)(m.ZP,{container:!0,spacing:2,children:[(0,N.jsx)(m.ZP,{item:!0,xs:6,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"edit-ruleset-code",label:"规则集编码",size:"small",fullWidth:!0,variant:"outlined",value:k.code,disabled:!0})}),(0,N.jsx)(m.ZP,{item:!0,xs:6,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"edit-ruleset-name",label:"规则集名称",size:"small",fullWidth:!0,variant:"outlined",value:k.name,onChange:function(e){return P(E(E({},k),{},{name:(0,I.f)(e.target.value)}))},inputProps:{maxLength:50},error:!!(null!=G&&G.name),helperText:null==G?void 0:G.name,onBlur:J})}),(0,N.jsx)(m.ZP,{item:!0,xs:9,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"edit-ruleset-defaultReturnValues",label:"默认返回值集合",size:"small",fullWidth:!0,variant:"outlined",value:k.defaultReturnValues,onChange:function(e){return P(E(E({},k),{},{defaultReturnValues:(0,I.f)(e.target.value)}))},inputProps:{maxLength:256},error:!!(null!=G&&G.defaultReturnValues),helperText:null==G?void 0:G.defaultReturnValues,onBlur:J})}),(0,N.jsx)(m.ZP,{item:!0,xs:9,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"edit-ruleset-remark",label:"规则集备注",size:"small",fullWidth:!0,variant:"outlined",value:k.remark,onChange:function(e){return P(E(E({},k),{},{remark:(0,I.f)(e.target.value)}))},inputProps:{maxLength:1024},error:!!(null!=G&&G.remark),helperText:null==G?void 0:G.remark,onBlur:J})}),(0,N.jsx)(m.ZP,{item:!0,xs:9,children:(0,N.jsx)(f.Z,{autoComplete:"off",id:"edit-ruleset-remark",label:"规则集表达式",size:"small",fullWidth:!0,variant:"outlined",value:k.expression,multiline:!0,rows:5,disabled:!0})}),B&&(0,N.jsx)(m.ZP,{item:!0,xs:12,children:(0,N.jsx)(j.Z,{severity:z,onClose:K,children:w})})]})}),(0,N.jsx)(Z.Z,{children:(0,N.jsx)(b.Z,{"aria-label":"editSave",onClick:Q,variant:"contained",color:"primary",startIcon:(0,N.jsx)(D.Z,{}),disabled:_,children:"保存"})})]})}function q(e){var t=e.selectedRow,n=e.openDialog,r=e.handleCloseDialog,a=e.mutate;return(0,N.jsx)(V.Z,{dialogName:"删除规则集",url:"admin/rulesetInfo/delete",initialParams:{id:t.id},openDialog:n,handleCloseDialog:r,mutate:a})}function F(e){var t=e.selectedRow,n=e.openDialog,r=e.handleCloseDialog,a=e.mutate;return(0,N.jsx)(V.Z,{dialogName:"刷新规则集",url:"admin/rulesetInfo/refresh",initialParams:{id:t.id},openDialog:n,handleCloseDialog:r,mutate:a})}function G(){var e=(0,c.useState)({page:0,pageSize:5}),t=e[0],n=e[1],r=(0,c.useState)({code:""}),a=r[0],l=r[1],o="admin/rulesetInfo/query?page=".concat(t.page,"&size=").concat(t.pageSize),i="".concat(o).concat((0,L.B5)(a)),s=(0,L.bf)(i),u=(0,z.ZP)(o,s,{revalidateOnFocus:!1,shouldRetryOnError:!1}),d=u.data,f=u.error,h=u.isLoading,x=u.mutate;return(0,N.jsx)("div",{style:{height:"auto",width:"100%"},children:(0,N.jsxs)(m.ZP,{container:!0,alignItems:"center",spacing:2,children:[(0,N.jsx)(B,{handleSearch:function(){x(void 0)},searchParam:a,setSearchParam:l}),(0,N.jsx)(W,{data:d,error:f,isLoading:h,paginationModel:t,setPaginationModel:n,mutate:x})]})})}var H=n(204);function $(){return(0,N.jsx)(r.Z,{menuItem:H.p[0],menuContent:(0,N.jsx)(G,{})})}}}]);