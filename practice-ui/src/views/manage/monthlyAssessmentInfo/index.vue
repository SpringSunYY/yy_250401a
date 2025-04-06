<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考核编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入考核编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核年份" prop="assessYear">
        <el-input
          v-model="queryParams.assessYear"
          placeholder="请输入考核年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核月份" prop="assessMonth">
        <el-input
          v-model="queryParams.assessMonth"
          placeholder="请输入考核月份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核得分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入考核得分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="创建人" prop="createBy">-->
<!--        <el-input-->
<!--          v-model="queryParams.createBy"-->
<!--          placeholder="请输入创建人"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="用户" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入用户"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="班级" prop="deptId">-->
<!--        <el-input-->
<!--          v-model="queryParams.deptId"-->
<!--          placeholder="请输入班级"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
<!--      <el-form-item label="更新人" prop="updateBy">-->
<!--        <el-input-->
<!--          v-model="queryParams.updateBy"-->
<!--          placeholder="请输入更新人"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新时间">-->
<!--        <el-date-picker-->
<!--          v-model="daterangeUpdateTime"-->
<!--          style="width: 240px"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['manage:monthlyAssessmentInfo:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:monthlyAssessmentInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:monthlyAssessmentInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:monthlyAssessmentInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="monthlyAssessmentInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="考核编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="公司编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="companyName"
      />
      <el-table-column label="考核年份" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="assessYear"
      />
      <el-table-column label="考核月份" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="assessMonth"
      />
      <el-table-column label="考核得分" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="score"
      />
      <el-table-column label="评语" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="comments"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="createBy"
      />
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="userName"
      />
      <el-table-column label="班级" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:monthlyAssessmentInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:monthlyAssessmentInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改月度考核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="公司编号" prop="companyId">-->
        <!--          <el-input v-model="form.companyId" placeholder="请输入公司编号"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="考核年份" prop="assessYear">
          <el-input v-model="form.assessYear" placeholder="请输入考核年份"/>
        </el-form-item>
        <el-form-item label="考核月份" prop="assessMonth">
          <el-input v-model="form.assessMonth" placeholder="请输入考核月份"/>
        </el-form-item>
        <el-form-item label="考核得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入考核得分"/>
        </el-form-item>
        <el-form-item label="评语" prop="comments">
          <el-input v-model="form.comments" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <!--        <el-form-item label="用户" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入用户"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMonthlyAssessmentInfo,
  getMonthlyAssessmentInfo,
  delMonthlyAssessmentInfo,
  addMonthlyAssessmentInfo,
  updateMonthlyAssessmentInfo
} from '@/api/manage/monthlyAssessmentInfo'

export default {
  name: 'MonthlyAssessmentInfo',
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '考核编号', visible: false },
        { key: 1, label: '公司编号', visible: true },
        { key: 2, label: '考核年份', visible: true },
        { key: 3, label: '考核月份', visible: true },
        { key: 4, label: '考核得分', visible: true },
        { key: 5, label: '评语', visible: true },
        { key: 6, label: '创建人', visible: true },
        { key: 7, label: '用户', visible: true },
        { key: 8, label: '班级', visible: true },
        { key: 9, label: '创建时间', visible: false },
        { key: 10, label: '更新人', visible: false },
        { key: 11, label: '更新时间', visible: false },
        { key: 12, label: '备注', visible: false }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 月度考核表格数据
      monthlyAssessmentInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        assessYear: null,
        assessMonth: null,
        score: null,
        comments: null,
        createBy: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: '公司编号不能为空', trigger: 'blur' }
        ],
        assessYear: [
          { required: true, message: '考核年份不能为空', trigger: 'blur' }
        ],
        assessMonth: [
          { required: true, message: '考核月份不能为空', trigger: 'blur' }
        ],
        score: [
          { required: true, message: '考核得分不能为空', trigger: 'blur' }
        ],
        createBy: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '用户不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '班级不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询月度考核列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listMonthlyAssessmentInfo(this.queryParams).then(response => {
        this.monthlyAssessmentInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        companyId: null,
        assessYear: null,
        assessMonth: null,
        score: null,
        comments: null,
        createBy: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加月度考核'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMonthlyAssessmentInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改月度考核'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMonthlyAssessmentInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addMonthlyAssessmentInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除月度考核编号为"' + ids + '"的数据项？').then(function() {
        return delMonthlyAssessmentInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/monthlyAssessmentInfo/export', {
        ...this.queryParams
      }, `monthlyAssessmentInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
