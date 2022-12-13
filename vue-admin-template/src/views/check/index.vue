<template>
    <div class="container">
        <div class="top">
            <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select" clearable>
                <el-select v-model="type" slot="prepend" placeholder="请选择">
                    <el-option label="检查单号" value="check_id"></el-option>
                    <el-option label="订单号" value="order_id"></el-option>
                </el-select>
                <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
            </el-input>
        </div>

        <div class="mid">
            <el-table :data="list" height="450">
                <el-table-column prop="check_id" label="检查单号"></el-table-column>
                <el-table-column prop="order_id" label="订单号"></el-table-column>
                <el-table-column prop="description" label="问题描述"></el-table-column>
                <el-table-column prop="pay" label="赔付金额" width="120" align="center"></el-table-column>
                <el-table-column prop="time" label="订单创建时间" align="center"></el-table-column>
                <el-table-column align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="details(scope.row)">详情</el-button>
                        <el-button size="mini" type="primary" @click="check(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="del(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-collapse>
                <el-collapse-item title="待处理检查单">
                    <el-table :data="list2">
                        <el-table-column prop="check_id" label="检查单号"></el-table-column>
                        <el-table-column prop="order_id" label="订单号"></el-table-column>
                        <el-table-column prop="indent.status" label="订单状态" width="120" align="center">
                            <template slot-scope="scope">
                                {{ scope.row.indent.status == 2 ? "待检查" : "完成" }}
                            </template>
                        </el-table-column>
                        <el-table-column align="center">
                            <template slot-scope="scope">
                                <el-button size="mini" type="primary" @click="check(scope.row)">处理</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-collapse-item>
            </el-collapse>

            <!-- check dialog -->
            <el-dialog title="检查" :visible.sync="checkDialog" width="30%" center :before-close="handleClose">
                <el-form label-width="120px">
                    <el-form-item label="检查单号">
                        {{ checks.check_id }}
                    </el-form-item>
                    <el-form-item label="订单号">
                        {{ checks.order_id }}
                    </el-form-item>
                    <el-form-item label="检查单时间">
                        {{ checks.time }}
                    </el-form-item>
                    <el-form-item label="问题描述">
                        <el-input v-model="checks.description" placeholder="问题描述" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="赔付金额">
                        <el-input v-model="checks.pay" placeholder="赔付金额" clearable></el-input>
                    </el-form-item>
                </el-form>

                <span slot="footer" class="dialog-footer">
                    <el-button @click="checkDialog = false">取 消</el-button>
                    <el-button type="primary" @click="doCheck()">确 定</el-button>
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
                    <el-form-item label="用户信息">
                        用户姓名:<span style="margin-right: 10px">{{ user.name }}</span><br>
                        电话号码:<span style="margin-right: 10px">{{ user.phone }}</span><br>
                        身份证号:<span style="margin-right: 10px">{{ user.identity }}</span><br>
                        用户性别:<span style="margin-right: 10px">{{ user.sex }}</span><br>
                    </el-form-item>
                </el-form>

                <span slot="footer" class="dialog-footer">
                    <el-button @click="detailsDialog = false">确 定</el-button>
                    <!-- <el-button type="primary" @click="doCheck()">确 定</el-button> -->
                </span>
            </el-dialog>
        </div>

    </div>
</template>
 
<script>
import { CheckList, CheckUpdate, CheckDelete, getByNumber, getByIdentity, CheckSearch } from '@/network/api';

export default {
    name: 'check',
    data() {
        return {
            list: [],
            list2: [],
            checks: {
                check_id: '',
                order_id: '',
                time: '',
                description: '',
                pay: ''
            },
            car: {
                number: '',
                type: '',
                brand: '',
                version: '',
                address: '',
                description: '',
            },
            user: {
                name: '',
                phone: '',
                identity: '',
                sex: '',
            },
            checkDialog: false,
            detailsDialog: false,
            searchText: '',
            type: ''
        }
    },
    methods: {
        check(row) {
            this.checks = Object.assign({}, row)
            this.checkDialog = true
        },
        doCheck() {
            console.log(this.checks)
            if (this.checks.description == null || this.check.description == '') {
                this.checks.description = '无'
            }
            if (this.checks.pay == null) {
                this.checks.pay = 0
            }
            CheckUpdate(this.checks).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                }
                this.flush()
            })
            this.checkDialog = false
        },
        del(row) {
            this.$confirm('确定删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //确认删除处理
                this.checks = row
                CheckDelete(this.checks).then(res => {
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
        details(row) {
            console.log(row)
            getByIdentity(row.indent.identity).then(res => {
                this.user = res.data
            })
            getByNumber(row.indent.number).then(res => {
                this.car = res.data
            })
            this.detailsDialog = true
        },
        search() {
            CheckSearch(this.type, this.searchText).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                    this.list = res.data
                } else {
                    this.flush()
                    this.$message.error(res.msg)
                }
            })
        },
        handleClose(done) {
            done()
            this.flush()
        },
        flush() {
            CheckList().then(res => {
                this.list = []
                this.list2 = []
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].indent.status == 2) {
                        this.list2.push(res.data[i])
                    } else {
                        this.list.push(res.data[i])
                    }
                }
            })
        }
    },
    created() {
        CheckList().then(res => {
            console.log(res)
            for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].indent.status == 2) {
                    this.list2.push(res.data[i])
                } else {
                    this.list.push(res.data[i])
                }
            }
        })
    }
}
</script>
 
<style scoped>
.container {
    width: 80%;
    margin: 20px auto;
}
.top .input-with-select {
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