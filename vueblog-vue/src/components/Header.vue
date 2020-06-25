<template>
  <el-header style="margin:10px 5px 20px 20px;">
  	<ul>
      <li>
         <el-avatar :size="30" src="https://wxt.sinaimg.cn/thumb300/005JLJQ4gy1gg20bmywlzj308z05mq32.jpg?tags=%5B%5D"></el-avatar>   
      </li>
       <li>
         <h4>Turato 博客</h4>
       </li>

  		<li>
         <el-link href="http://www.baidu.com" icon="el-icon-search" type="info">百度搜索</el-link>

        <el-divider direction="vertical"></el-divider>

        <span><el-link href="/blogs" icon="el-icon-s-home">主页</el-link></span>
       
        <el-divider direction="vertical"></el-divider>
        
        <span><el-link  style=".el-link.el-link--default: #ff00ff" type="success" href="/blog/add" icon="el-icon-reading">发表博客</el-link></span>

        <el-divider direction="vertical"></el-divider>

        <span v-show="!hasLogin"><el-link type="primary" href="/login" icon="el-icon-user">登录</el-link></span> 
        
        <span v-show="hasLogin"><el-link type="danger" @click="logout" icon="el-icon-user">退出</el-link></span>   
        
        <el-divider direction="vertical"></el-divider>
        
        <span><el-link  style=".el-link.el-link--default: #FFFFFF" type="success" href="/blogs/search" icon="el-icon-search">搜索文章</el-link></span>
        
        <el-divider direction="vertical"></el-divider>
  		</li>
  	</ul>
  </el-header>
</template>
<style scoped>
h4{
    padding: 20px;
    margin: 20px
}
ul{
	list-style-type: none; /*//消除黑点*/	
  text-align: center;
	margin:0;
  width:100%;
  display: block;
  list-style-type: disc;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  padding-inline-start: 40px;
}
li{
	display: inline-block;
	margin:0 auto;
}
a{
	color: #fff;/*设置字体颜色*/
	text-decoration: none;/*消除下划线*/
	padding: 12px;
	border-radius: 5px;
}
nav{
	background: grey;
	padding: 0 0;
  width:105%;
  margin:auto auto;
  position: center;
}
/*选中则发生一些变化*/
.router-link-active{
	background: rgba(255,255,255,0.8);
	color: #444;
}

  .m-content {
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
  }
  .maction {
    margin: 10px 0;
  }

</style>
<script>
  export default {
    name: "Header",
    data() {
      return {
        words: "",
        user: {
          username: '请先登录',
          avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        },
        hasLogin: false
      }
    },
    methods: {
      logout() {
        const _this = this
        _this.$axios.get("/logout", {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(res => {
          _this.$store.commit("REMOVE_INFO")
          _this.$router.push("/login")

        })
      }
    },
    created() {
      if(this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.avatar = this.$store.getters.getUser.avatar
        this.hasLogin = true
      }

    },

  }
</script>