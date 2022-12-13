<template>
  <div class="container">
    <div class="top">
      <div class="left">
        <el-row>
          <el-button type="primary" plain @click="addDialog = true">新增车辆</el-button>
          <el-button type="danger" plain style="margin-left: 30px" @click="delIds()">批量删除</el-button>
        </el-row>
      </div>
      <div class="right">
        <el-input placeholder="请输入车牌号" v-model="searchText" class="input-with-select" clearable>
          <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
        </el-input>
      </div>
    </div>

    <div class="mid">
      <el-table ref="multipleTable" :data="list">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column prop="number" label="车牌号" width="130" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" width="100" align="center" :filters="gettype()"
          :filter-method="filterTag"></el-table-column>
        <el-table-column prop="brand" label="品牌" :filters="getbrand()" :filter-method="filterTag"></el-table-column>
        <el-table-column prop="version" label="型号"></el-table-column>
        <el-table-column prop="color" label="颜色" width="90" align="center" :filters="getcolor()"
          :filter-method="filterTag"></el-table-column>
        <el-table-column prop="image" label="图片" width="150px" align="center">
          <template slot-scope="scope">
            <el-image style="width: 120px; height: 70px" :src="ipadd + scope.row.image" :preview-src-list="[
              ipadd + scope.row.image,
              ipadd + scope.row.image
            ]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="in_price" label="购车价格" width="120" sortable></el-table-column>
        <el-table-column prop="out_price" label="租赁价格" width="120" sortable></el-table-column>
        <el-table-column prop="address" label="城市"> </el-table-column>
        <el-table-column prop="description" label="描述"> </el-table-column>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template slot-scope="scope">
            {{ scope.row.status == '1' ? "未出租" : "已出租" }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="250">
          <template slot-scope="scope">
            <el-button size="mini" @click="toEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 新增 -->
      <el-dialog title="新增车辆" :visible.sync="addDialog" width="30%" center :before-close="handleClose">
        <el-form label-width="80px">

          <el-upload class="avatar-uploader" action="http://localhost:8080/uploadCar" :show-file-list="false"
            :headers="headers" :on-success="uploadSuccess">
            <img v-if="car.image" :src="ipadd + car.image" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

          <el-form-item label="车牌号">
            <el-input v-model="car.number" clearable></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-input v-model="car.type" clearable></el-input>
          </el-form-item>
          <el-form-item label="品牌">
            <el-input v-model="car.brand" clearable></el-input>
          </el-form-item>
          <el-form-item label="型号">
            <el-input v-model="car.version" clearable></el-input>
          </el-form-item>
          <el-form-item label="颜色">
            <el-input v-model="car.color" clearable></el-input>
          </el-form-item>
          <el-form-item label="购车价格">
            <el-input v-model="car.in_price"></el-input>
          </el-form-item>
          <el-form-item label="租赁价格">
            <el-input v-model="car.out_price" clearable></el-input>
          </el-form-item>
          <el-form-item label="所在地">
            <el-input v-model="car.address" clearable></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="car.description" clearable></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialog = false">取 消</el-button>
          <el-button type="primary" @click="add()">确 定</el-button>
        </span>
      </el-dialog>

      <!-- editDialog -->
      <el-dialog title="修改车辆信息" :visible.sync="editDialog" width="30%" center :before-close="handleClose">
        <el-form label-width="80px">
          <el-upload class="avatar-uploader" action="http://localhost:8080/uploadCar" :show-file-list="false"
            :headers="headers" :on-success="uploadSuccess">
            <img v-if="car.image" :src="ipadd + car.image" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

          <template v-if="role == 0">
            <el-form-item prop="id" label="车牌号">
              <el-input v-model="car.number" clearable></el-input>
            </el-form-item>
          </template>
          <el-form-item label="类型">
            <el-input v-model="car.type" clearable></el-input>
          </el-form-item>
          <el-form-item label="品牌">
            <el-input v-model="car.brand" clearable></el-input>
          </el-form-item>
          <el-form-item label="型号">
            <el-input v-model="car.version" clearable></el-input>
          </el-form-item>
          <el-form-item label="颜色">
            <el-input v-model="car.color" clearable></el-input>
          </el-form-item>
          <template v-if="role == 0">
            <el-form-item label="购车价格">
              <el-input v-model="car.in_price"></el-input>
            </el-form-item>
          </template>
          <el-form-item label="租赁价格">
            <el-input v-model="car.out_price" clearable></el-input>
          </el-form-item>
          <el-form-item label="所在地">
            <el-input v-model="car.address" clearable></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="car.description" clearable></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="car.status">
              <el-radio :label="1">未出租</el-radio>
              <el-radio :label="2">已出租</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="editNo()">取 消</el-button>
          <el-button type="primary" @click="editDo()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { Carlist, CarAdd, CarUpdate, CarDelete, CarDelIds, CarSearch, CarBrand, CarType, CarColor } from '@/network/api'
import { getCache } from '@/utils/cache'

export default {
  name: 'car',
  data() {
    return {
      list: [],
      car: {
        id: '',
        number: '',
        type: '',
        brand: '',
        version: '',
        color: '',
        in_price: '',
        out_price: '',
        address: '',
        description: '',
        image: '',
        status: ''
      },
      searchText: '',
      headers: { token: getCache('token') },
      ipadd: 'http://localhost:8080/',
      addDialog: false,
      editDialog: false,
      brandArr: []
    }
  },
  computed: {
    role() {
      return !!getCache('role')
    }
  },
  methods: {
    add() {
      console.log(this.car)
      CarAdd(this.car).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg)
        }
        this.flush()
        this.addDialog = false
      })
    },
    search() {
      CarSearch(this.searchText).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message.success(res.msg)
          this.list = res.data
        } else {
          this.flush()
          this.$message.error(res.msg)
        }
      })
    },
    toEdit(row) {
      this.car = Object.assign({}, row)
      this.editDialog = true
      console.log(this.car)
    },
    editDo() {
      CarUpdate(this.car).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg)
        }
        this.flush()
      })
      this.editDialog = false
    },
    editNo() {
      this.flush()
      this.editDialog = false
    },
    del(row) {
      this.$confirm('确定删除用户?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确认删除处理
        this.car = row
        // console.log(this.car)
        CarDelete(this.car).then(res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
          } else {
            this.$message.error(res.msg)
          }
          this.flush()
        })
      }).catch(() => {
        this.$message.info('取消删除')
      });
    },
    delIds() {
      let sel = this.$refs.multipleTable.selection
      let ids = new Array()
      for (let i = 0; i < sel.length; i++) {
        ids.push(sel[i].id)
      }
      console.log(ids)
      CarDelIds({ 'ids': ids }).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg)
        }
        this.flush()
      })
    },
    uploadSuccess(res) {
      let path = 'http://localhost:8080/' + res.data
      this.car.image = res.data;
    },
    handleClose(done) {
      done()
      this.flush()
    },
    gettype() {
      let arr = []
      CarType().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          arr.push({ text: res.data[i].type, value: res.data[i].type })
        }
      })
      return arr
    },
    getbrand() {
      let arr = []
      CarBrand().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          arr.push({ text: res.data[i].brand, value: res.data[i].brand })
        }
      })
      return arr
    },
    getcolor() {
      let arr = []
      CarColor().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          arr.push({ text: res.data[i].color, value: res.data[i].color })
        }
      })
      return arr
    },
    filterTag(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    flush() {
      Carlist().then((res) => {
        this.list = res.data
        this.car = {
          id: '',
          number: '',
          type: '',
          brand: '',
          version: '',
          color: '',
          in_price: '',
          out_price: '',
          address: '',
          description: '',
          image: '',
          status: ''
        }
      })
    }
  },
  created() {
    Carlist().then(res => {
      this.list = res.data
    })
  }
}
</script>

<style scoped>
.container {
  width: 90%;
  margin: 40px auto;
}

.top .left {
  display: inline-block;
  width: 65%;
}

.top .right {
  display: inline-block;
  width: 30%;
  margin-left: 4%;
}


.mid .el-table {
  width: 100%;
  margin: 20px auto;
}

.avatar-uploader /deep/ .el-upload {
  width: 270px;
  height: 170px;
  margin-left: 25%;
  border: 1px dashed #cac5c5;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin-bottom: 20px;
}

.avatar-uploader /deep/ .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 270px;
  height: 170px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 270px;
  height: 170px;
  display: block;
}
</style>
