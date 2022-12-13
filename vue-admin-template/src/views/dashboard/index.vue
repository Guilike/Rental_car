<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎您: {{ name }}</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCache } from '@/utils/cache'
export default {
  name: 'Dashboard',
  data(){
    return {
      name: '',
    }
  },
  // computed: {
  //   ...mapGetters([
  //     'name'
  //   ])
  // },
  mounted(){
    if (location.href.indexOf("#reloaded") == -1) {
      location.href = location.href + "#reloaded";
      location.reload();
    }
    this.name = getCache('uname')
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
