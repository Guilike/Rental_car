<template>
    <div class="container">
        <div class="top">
            <el-date-picker v-model="timevalue" type="month" value-format="yyyy-MM" placeholder="租赁开始时间"
                style="margin-left:52%">
            </el-date-picker>

            <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select" clearable>
                <el-select v-model="type" slot="prepend" placeholder="请选择">
                    <el-option label="订单号" value="order_id"></el-option>
                    <el-option label="车牌号" value="number"></el-option>
                </el-select>
                <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
            </el-input>
        </div>

        <div class="mid">
            <el-table :data="list">
                <el-table-column prop="order_id" label="订单号"></el-table-column>
                <el-table-column prop="number" label="车牌号" align="center"></el-table-column>
                <el-table-column prop="identity" label="身份证号" align="center"></el-table-column>
                <el-table-column prop="start_time" label="租赁开始时间" align="center"></el-table-column>
                <el-table-column prop="end_time" label="租赁结束时间" align="center"></el-table-column>
                <el-table-column prop="time" label="订单创建时间" align="center"></el-table-column>
                <el-table-column prop="price" label="订单价格" width="120" align="center"></el-table-column>
                <el-table-column prop="status" label="订单状态" width="120" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.status == 1 ? "租赁中" : scope.row.status == 2 ? "检查中" : scope.row.status == 3 ? "完成":"取消订单" }}
                    </template>
                </el-table-column>
                <el-table-column align="center" width="220">
                    <template slot-scope="scope">
                        <el-button size="mini" plain @click="details(scope.row)">详情</el-button>
                        <el-button size="mini" type="primary" plain @click="restore(scope.row)">还车</el-button>
                        <el-button size="mini" type="success" plain @click="renewal(scope.row)">续约</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 续约dialog -->
            <el-dialog title="续约" :visible.sync="renewalDialog" width="24%" center :before-close="handleClose">
                <el-form label-width="120px">
                    <el-form-item label="开始时间">
                        <span>{{ indent.start_time }}</span>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-date-picker v-model="indent.end_time" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间" :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-form-item>
                </el-form>

                <span slot="footer" class="dialog-footer">
                    <el-button @click="renewalDialog = false">取 消</el-button>
                    <el-button type="primary" @click="renewalDo()">确 定</el-button>
                </span>
            </el-dialog>

            <!-- details dialog -->
            <el-dialog title="详情" :visible.sync="detailsDialog" width="25%" center :before-close="handleClose">
                <el-form label-width="120px">
                    <el-form-item label="车辆信息">
                        车牌:<span style="margin-right: 10px">{{ car.number }}</span><br>
                        类型:<span style="margin-right: 10px">{{ car.type }}</span><br>
                        品牌:<span style="margin-right: 10px">{{ car.brand }}</span><br>
                        型号:<span style="margin-right: 10px">{{ car.version }}</span><br>
                        地址:<span style="margin-right: 10px">{{ car.address }}</span><br>
                        描述:<span style="margin-right: 10px">{{ car.description }}</span><br>
                    </el-form-item>
                    <el-form-item label="检查单信息">
                        检查单时间:<span style="margin-right: 10px">{{ checks.time }}</span><br>
                        问题描述:<span style="margin-right: 10px">{{ checks.description }}</span><br>
                        赔付金额:<span style="margin-right: 10px">{{ checks.pay }}</span><br>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="danger" size="mini" @click="cancel()">取消订单</el-button>
                    </el-form-item>
                </el-form>
                <!-- <span slot="footer" class="dialog-footer">
                    <el-button >续 约</el-button>
                    <el-button >取消订单</el-button>
                </span> -->
            </el-dialog>
        </div>
    </div>
</template>
 
<script>
import { IndentUserList, IndentSearch, IndentUpdate, CheckAdd, getByNumber, getByOrderId, IndentCancel } from '@/network/api'
import { getCache } from '@/utils/cache'

export default {
    name: 'indent',
    data() {
        return {
            list: [],
            indent: {
                order_id: '',
                number: '',
                identity: '',
                price: '',
                status: '',
                start_time: '',
                end_time: '',
                time: '',
            },
            car: {
                number: '',
                type: '',
                brand: '',
                version: '',
                address: '',
                description: '',
            },
            checks: {
                time: '',
                description: '',
                pay: ''
            },
            timevalue: '',
            type: '',
            searchText: '',
            detailsDialog: false,
            renewalDialog: false,
            pickerOptions: {
                disabledDate(v) {
                    return v.getTime() < new Date().getTime() - 86400000
                }
            }
        }
    },
    methods: {
        search() {
            IndentSearch(this.type, this.searchText, this.timevalue).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                    this.list = res.data
                } else {
                    this.flush()
                    this.$message.error(res.msg)
                }
            })
        },
        details(row) {
            // console.log(row)
            this.indent = Object.assign({},row)
            console.log(this.indent)
            getByNumber(row.number).then(res => {
                this.car = res.data
            })
            if (row.status == 3) {
                getByOrderId(row.order_id).then(res => {
                    this.checks = res.data
                })
            } else {
                this.checks.time = '暂无'
                this.checks.description = '暂无'
                this.checks.pay = '暂无'
            }

            // console.log(this.checks)
            this.detailsDialog = true
        },
        cancel() {
            console.log(this.indent)
            IndentCancel(this.indent).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        restore(row) {
            if (row.status == 1) {
                this.$confirm('确定还车?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //确认处理
                    CheckAdd({ 'order_id': row.order_id }).then(res => {
                        if (res.code === 200) {
                            this.$message.success('已还车,待检查')
                        }
                        this.flush()
                    })
                }).catch(() => {
                    this.$message.info('已取消')
                });
            } else {
                this.$message.error('不能重复还车')
            }

        },
        renewal(row) {
            console.log(row)
            if (row.status == 1) {
                this.indent = row
                this.renewalDialog = true
            } else {
                this.$message.error('当前状态不可续约')
            }

        },
        renewalDo() {
            IndentUpdate(this.indent).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                }
                this.flush()
            })
            this.renewalDialog = false
        },
        handleClose(done) {
            done()
            this.flush()
        },
        flush() {
            IndentUserList(getCache('identity')).then(res => {
                this.list = res.data
            })
            this.car = {}
            this.checks = {}
        }
    },
    created() {
        IndentUserList(getCache('identity')).then(res => {
            this.list = res.data
        })
    }
}
</script>
 
<style scoped>
.container {
    width: 90%;
    margin: 20px auto;
}

.top {
    position: relative;
}

.top .input-with-select {
    position: absolute;
    right: 0;
    margin-left: 40px;
    width: 30%;
}

.top .el-select {
    width: 120px;
}

.mid {
    margin: 20px auto;
}
</style>