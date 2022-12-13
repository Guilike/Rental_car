<template>
  <div id="app">
    <router-view />
    <!-- <el-button @click="test1()">test</el-button> -->
  </div>
</template>

<script>
// import Axios from 'axios';
// import {test} from '@/network/api'
export default {
  name: 'App',
  methods: {
    test1(){
      // Axios.get('http://localhost:8080/test').then(res=>{
      //   console.log(res)
      // }).catch(error=>{
      //   console.log(error);
      // });
      test({
        username:"admin",
        password:"123456"
      }).then(res=>{
        console.log(res);
      })
    },
  },
};
</script>
