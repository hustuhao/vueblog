<template>
<!-- 思路就是单独一个页面来做文章搜索功能，主页面和 Blogs 视图类似 -->
  <div class="mcontaner">
      <Header></Header>
              <el-input
                placeholder="搜索文章"
                v-model="words"
                clearable
                @keyup.enter.native="searchBlog"
                style="margin:10px 5px 20px 20px;">
              </el-input>
              
              <!--普通的搜索框 <input v-model="words" @keyup.enter="searchBlog"> -->

          <div class="block"> 
              <el-timeline>
              <el-timeline-item :timestamp="blog.created" placement="top" v-for="(blog,index) in blogs" :key="index" >
                <el-card>
                    <h4>
                    <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                        {{blog.title}}
                    </router-link>
                    </h4>
                    <p>{{blog.description}}</p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
            <el-pagination class="mpage"
                            background
                            layout="prev, pager, next"
                            :current-page="currentPage"
                            :page-size="pageSize"
                            :total="total"
                            @current-change=page>
            </el-pagination>  
        </div>
  </div>
</template>

<script>
  import Header from "../components/Header";

  export default {
    name: "BlogSearch.vue",
    components: {Header},// 引入 header
    data() {
      return {
        words: "", //搜索关键词
        blogs: {},
        currentPage: 1,
        total: 0,
        pageSize: 5
      }
    },
    methods: {
      // 页面初始化，显示所有的文章
      page(currentPage) {
        const _this = this
        //  "blogs/search?words=" + words+"&currentPage=" + currentPage
        _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
          console.log(res)
          _this.blogs = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size

        })
      },
      //搜索文章功能, 后端API已完成，前端页面未完成
    searchBlog(){
      const _this = this;
      var words = _this.words; //注意return 中的变量必须用this来引用
      var currentPage = _this.currentPage;
      console.log(words);
      //如果搜索关键字 words 不为空
      if(words) {
        this.$axios.get("/blogs/search?words=" + words+"&currentPage=" + currentPage).then(res => {
          console.log(res)
          _this.blogs = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size
          // 查找到博客
          if(_this.total != 0){
            ;
          } else {
            alert("找不到记录，请换个词查找哦！(づ￣3￣)づ╭❤");
            //实现所有的博客
            _this.page(1);
          }
        });
      }else{
        //如果关键字为空就显示所有的博客
        _this.page(1);
      }
    }
    },
    created() {
      this.page(1)
    }
    
  }
</script>

<style scoped>

  .mpage {
    margin: 0 auto;
    text-align: center;
  }

</style>