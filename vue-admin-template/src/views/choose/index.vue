<template>
    <div class="container">
        <div class="top">
            <el-select v-model="value" clearable @change="changeValue" placeholder="请选择城市" style="margin-right: 10%">
                <el-option v-for="item in address" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>

            <el-date-picker v-model="timevalue" type="datetimerange" align="left" start-placeholder="开始日期"
                end-placeholder="结束日期" :default-time="['09:00:00', '09:00:00']" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="pickerOptions">
                <!-- :picker-options="pickerOptions" -->
            </el-date-picker>
            <div style="margin-top: 20px;color: darkgray;">
                <span>tips:租车时长不足一天按照一天费用计算</span>
            </div>

        </div>

        <div class="mid">
            <el-table :data="list">
                <el-table-column prop="number" label="车牌号" width="130" align="center"></el-table-column>
                <el-table-column prop="type" label="类型" width="100" align="center"></el-table-column>
                <el-table-column prop="brand" label="品牌">
                </el-table-column>
                <el-table-column prop="version" label="型号"></el-table-column>
                <el-table-column prop="color" label="颜色" width="90" align="center"></el-table-column>
                <el-table-column prop="image" label="图片" width="150px" align="center">
                    <template slot-scope="scope">
                        <el-image style="width: 120px; height: 70px" :src="ipadd + scope.row.image" :preview-src-list="[
                            ipadd + scope.row.image,
                            ipadd + scope.row.image
                        ]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="out_price" label="价格/天" align="center" width="120" sortable></el-table-column>
                <el-table-column prop="address" label="城市"> </el-table-column>
                <el-table-column prop="description" label="描述"> </el-table-column>
                <el-table-column align="center" width="250">
                    <template slot-scope="scope">
                        <el-button type="success" plain @click="sure(scope.row)">租赁</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 确认租赁 -->
            <el-dialog title="确认租赁" :visible.sync="sureDialog" width="30%" center :before-close="handleClose">
                <el-form label-width="120px">
                    <el-form-item label="车辆信息">
                        <span>车牌号:{{ car.number }}</span>
                        <span style="margin-left:10px">品牌:{{ car.brand }}</span>
                        <span style="margin-left:10px">型号:{{ car.type }}</span>
                    </el-form-item>
                    <el-form-item label="租赁时间">
                        <span>{{ timevalue[0] }} 至 {{ timevalue[1] }}</span>
                    </el-form-item>
                    <el-form-item label="租赁总天数">
                        <span>{{ Info.days }} 天</span>
                    </el-form-item>
                    <el-form-item label="租赁总价格">
                        <span>{{ Info.price }} 元</span>
                    </el-form-item>
                </el-form>

                <span slot="footer" class="dialog-footer">
                    <el-button @click="sureDialog = false">取 消</el-button>
                    <el-button type="primary" @click="add()">确 定</el-button>
                </span>
            </el-dialog>

        </div>
    </div>
</template>
 
<script>
import { CarOutList, CarAddress, searchAddress, IndentpriceAndDays, IndentAdd } from '@/network/api'
import { getCache } from '@/utils/cache'

export default {
    name: 'choose',
    data() {
        return {
            list: [],
            address: [],
            Info: {
                days: '',
                price: '',
            },
            indent: {
                number: '',
                identity: '',
                start_time: '',
                end_time: ''
            },
            car: {
                number: '',
                brand: '',
                type: ''
            },
            value: '',
            sureDialog: false,
            ipadd: 'http://localhost:8080/',
            timevalue: '',
            pickerOptions: {
                disabledDate(v) {
                    return v.getTime() < new Date().getTime() - 86400000
                }
            }
        }
    },
    methods: {
        changeValue(value) {
            // console.log(this.timevalue)
            searchAddress(value).then(res => {
                this.list = res.data
            })
        },
        sure(row) {
            if (this.timevalue == null || this.timevalue == '') {
                this.$message.error("请选择租赁时间")
                return
            }

            this.car = Object.assign({}, row)
            this.sureDialog = true
            this.indent.number = row.number
            this.indent.identity = getCache('identity')
            this.indent.start_time = this.timevalue[0]
            this.indent.end_time = this.timevalue[1]
            IndentpriceAndDays(this.indent).then(res => {
                this.Info = res.data
            })
            console.log(this.Info)
        },
        add() {
            IndentAdd(this.indent).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                } else {
                    this.$message.error(res.msg)
                }
                this.flush()
            })
            this.sureDialog = false
        },
        handleClose(done) {
            done()
            this.flush()
        },
        flush() {
            CarOutList().then(res => {
                this.list = res.data
            })
            
        }
    },
    created() {
        CarOutList().then(res => {
            this.list = res.data
        })
        CarAddress().then(res => {
            // console.log(res)
            for (let i = 0; i < res.data.length; i++) {
                this.address.push({ value: res.data[i].address, label: res.data[i].address })
            }
            // console.log(this.car)
        })
    }
}
</script>
 
<style scoped>
.container {
    width: 80%;
    margin: 40px auto;
}

.top {
    width: 50%;
    margin: 20px auto;
}

.mid .el-table {
    width: 100%;
    margin: 20px auto;
}
</style>