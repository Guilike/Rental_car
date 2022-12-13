<template>
    <div class="container">
        <div class="left">
            <h3>个人信息</h3>
            <el-divider></el-divider>
            <el-form label-width="150px">
                <el-upload class="avatar-uploader" action="http://localhost:8080/uploadIcon" :show-file-list="false"
                    :headers="headers" :on-success="uploadSuccess">
                    <img v-if="user.icon" :src="ipadd + user.icon" class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <el-form-item label="用户名">
                    <span>{{ user.username }}</span>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="user.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input v-model="user.phone" maxlength="11" clearable></el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="user.identity" maxlength="18" clearable></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="user.sex" placeholder="请选择">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色">
                    <span>{{ user.role == 0 ? "超级管理员" : user.role == 1 ? "管理员" : "普通用户" }}</span>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" plain @click="update()">修改</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="right">
            <h3>修改密码</h3>
            <el-divider></el-divider>
            <el-form label-width="150px">
                <el-form-item label="原密码">
                    <el-input type="password" v-model="change.origin" clearable></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input type="password" v-model="change.new" clearable></el-input>
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-input type="password" v-model="change.sure" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" plain @click="updatePwd()">修改密码</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
 
<script>
import { PersonInfo, PersonUpdate, PersonUpdatePassword } from '@/network/api'
import { getCache } from '@/utils/cache'
export default {
    name: 'person',
    data() {
        return {
            user: {
                id: '',
                username: '',
                password: '',
                name: '',
                phone: '',
                identity: '',
                sex: '',
                role: '',
                icon: ''
            },
            change: {
                username: '',
                origin: '',
                new: '',
                sure: ''
            },
            headers: { token: getCache('token') },
            ipadd: 'http://localhost:8080/'
        }
    },
    methods: {
        update() {
            console.log(this.user)
            PersonUpdate(this.user).then(res => {
                if (res.code == 200) {
                    this.$message.success(res.msg);
                } else {
                    this.$message.error(res.msg);
                }
                this.flush()
            })
        },
        updatePwd() {
            if (this.change.new != this.change.sure) {
                this.$message.error("输入新密码不一致");
                return;
            }
            this.change.username = this.user.username
            PersonUpdatePassword(this.change).then(res => {
                if (res.code == 200) {
                    this.$message.success(res.msg);
                } else {
                    this.$message.error(res.msg);
                }
                this.change = {}
            })
        },
        uploadSuccess(res) {
            this.user.icon = res.data
        },
        flush() {
            PersonInfo(getCache('uname')).then(res => {
                this.user = res.data
                // console.log(this.user)
            })
        }
    },
    mounted() {
        PersonInfo(getCache('uname')).then(res => {
            this.user = res.data
            // console.log(this.user)
        })
    }

}
</script>
 
<style scoped>
.container {
    width: 80%;
    margin-top: 20px;
    margin-left: 20px;
    display: flex;
    justify-content: space-around;
}

.left {
    width: 40%;
}

.right {
    width: 40%;
}


.avatar-uploader /deep/ .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-left: 45%;
    margin-bottom: 30px;
}

.avatar-uploader /deep/ .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>