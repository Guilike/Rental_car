<template>
    <div class="container">
        <div class="top">
            <el-button type="danger" plain style="margin-left: 30px" @click="delIds()">删除选中</el-button>
            <el-date-picker v-model="timevalue" type="month" value-format="yyyy-MM" placeholder="租赁开始时间"
                style="margin-left:45%">
            </el-date-picker>

            <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select" clearable>
                <el-select v-model="type" slot="prepend" placeholder="请选择">
                    <el-option label="订单号" value="order_id"></el-option>
                    <el-option label="车牌号" value="number"></el-option>
                    <el-option label="身份证号" value="identity"></el-option>
                </el-select>
                <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
            </el-input>
        </div>

        <div class="mid">
            <el-table ref="multipleTable" :data="list">
                <el-table-column type="selection" align="center">
                </el-table-column>
                <el-table-column prop="order_id" label="订单号"></el-table-column>
                <el-table-column prop="number" label="车牌号" align="center"></el-table-column>
                <el-table-column prop="identity" label="身份证号" align="center"></el-table-column>
                <el-table-column prop="start_time" label="租赁开始时间" align="center"></el-table-column>
                <el-table-column prop="end_time" label="租赁结束时间" align="center"></el-table-column>
                <el-table-column prop="time" label="订单创建时间" align="center"></el-table-column>
                <el-table-column prop="price" label="订单价格" align="center"></el-table-column>
                <el-table-column prop="status" label="订单状态" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.status == 1 ? "租赁中" : scope.row.status == 2 ? "检查中" : scope.row.status == 3 ? "完成":"取消订单" }}
                        
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
 
<script>
import { IndentList, IndentSearch, IndentDelIds } from '@/network/api';

export default {
    name: 'orders',
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
            timevalue: '',
            type: '',
            searchText: ''
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
        delIds() {
            let sel = this.$refs.multipleTable.selection
            let ids = new Array()
            for (let i = 0; i < sel.length; i++) {
                ids.push(sel[i].order_id)
            }
            console.log(ids)
            IndentDelIds({ 'ids': ids }).then(res => {
                if (res.code === 200) {
                    this.$message.success(res.msg)
                } else {
                    this.$message.error(res.msg)
                }
                this.flush()
            })
        },
        flush() {
            IndentList().then(res => {
                this.list = res.data
            })
        }
    },
    created() {
        IndentList().then(res => {
            console.log(res)
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