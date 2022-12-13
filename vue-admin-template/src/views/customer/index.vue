<template>
    <div>
        <div class="top">
            <div class="left">
                <el-row>
                    <el-button type="primary" plain @click="addDialog = true">新增客户</el-button>
                    <!-- <el-button type="danger" plain style="margin-left: 30px" @click="delIds()">批量删除</el-button> -->
                </el-row>
            </div>
            <div class="right">
                <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select" clearable>
                    <el-select v-model="type" slot="prepend" placeholder="请选择">
                        <el-option label="用户姓名" value="name"></el-option>
                        <el-option label="手机号码" value="phone"></el-option>
                        <el-option label="身份证号" value="identity"></el-option>
                    </el-select>
                    <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
                </el-input>
            </div>
        </div>

        <div class="mid">
            <el-table ref="multipleTable" :data="list" style="width: 80%; margin: 20px auto;font-size: 18px;">
                <!-- <el-table-column type="selection" align="center">
                </el-table-column> -->
                <el-table-column prop="id" label="序号" align="center">
                </el-table-column>
                <el-table-column prop="name" label="用户姓名">
                </el-table-column>
                <el-table-column prop="phone" label="手机号码">
                </el-table-column>
                <el-table-column prop="identity" label="身份证号">
                </el-table-column>
                <el-table-column prop="sex" label="性别" align="center">
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="toEdit(scope.row)">编辑</el-button>
                        <!-- <el-button size="mini" type="danger" @click="del(scope.row)">删除</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 新增 -->
        <el-dialog title="新增客户" :visible.sync="addDialog" width="30%" center :before-close="handleClose">
            <el-form label-width="80px">
                <el-form-item label="客户账号">
                    <el-input v-model="user.username" placeholder="客户账号" clearable></el-input>
                </el-form-item>
                <el-form-item label="客户姓名">
                    <el-input v-model="user.name" placeholder="客户姓名" clearable></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input v-model="user.phone" placeholder="手机号码" maxlength="11" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="user.identity" placeholder="身份证号" maxlength="18" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="user.sex" placeholder="请选择">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialog = false">取 消</el-button>
                <el-button type="primary" @click="add()">确 定</el-button>
            </span>
        </el-dialog>

        <!-- editDialog -->
        <el-dialog class="editDialog" title="修改客户信息" :visible.sync="editDialog" width="30%" center
            :before-close="handleClose">
            <el-form label-width="80px">
                <el-form-item label="客户姓名">
                    <el-input v-model="user.name" placeholder="客户姓名" clearable></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input v-model="user.phone" placeholder="手机号码" maxlength="11" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="user.identity" placeholder="身份证号" maxlength="18" clearable>
                    </el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="user.sex">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="editNo()">取 消</el-button>
                <el-button type="primary" @click="editDo()">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>
 
<script>
import { CustomerInfoList, CustomerSearch, CustomerAdd, CustomerDelete, CustomerUpdate, CustomerDelIds } from '@/network/api'


export default {
    name: 'customer',
    data() {
        return {
            list: [],
            user: {
                id: '',
                username: '',
                name: '',
                phone: '',
                identity: '',
                sex: '',
            },
            addDialog: false,
            editDialog: false,
            searchText: '',
            type: ''
        }
    },
    methods: {
        add() {
            CustomerAdd(this.user).then(res => {
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
        delIds() {
            let sel = this.$refs.multipleTable.selection
            let ids = new Array()
            for (let i = 0; i < sel.length; i++) {
                ids.push(sel[i].id)
            }
            console.log(ids)
            CustomerDelIds({ 'ids': ids }).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                } else {
                    this.$message.error(res.msg)
                }
                this.flush()
            })
        },
        search() {
            CustomerSearch(this.type, this.searchText).then(res => {
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
            this.user = Object.assign({}, row)
            this.editDialog = true
            console.log(this.user)
        },
        editDo() {
            CustomerUpdate(this.user).then(res => {
                // console.log(res)
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
        handleClose(done) {
            done()
            this.flush()
        },
        del(row) {
            this.$confirm('确定删除用户?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //确认删除处理
                this.user = row
                console.log(this.user)
                CustomerDelete(this.user).then(res => {
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
        flush() {
            CustomerInfoList().then(res => {
                this.list = res.data
                this.user = {}
            })
        }
    },
    created() {
        CustomerInfoList().then(res => {
            this.list = res.data
            this.user = {}
        })
    }
}
</script>
 
<style  scoped>
.top {
    width: 80%;
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

.top .right .el-input {
    width: 100%;
}

.top .right .el-select {
    width: 120px;
}
</style>