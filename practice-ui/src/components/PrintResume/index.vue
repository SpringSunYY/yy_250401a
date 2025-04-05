<template>
  <div id="print-resume" ref="printArea">
    <!-- 打印区域 -->
    <div id="print-resume" ref="printArea">
      <h2 style="text-align:center;">个人简历</h2>
      <table border="1" cellpadding="10" cellspacing="0" width="100%">
        <tr>
          <th>姓名</th>
          <td>{{ resume.fullName }}</td>
        </tr>
        <tr>
          <th>联系电话</th>
          <td>{{ resume.phone || '—' }}</td>
        </tr>
        <tr>
          <th>电子邮箱</th>
          <td>{{ resume.email || '—' }}</td>
        </tr>
        <tr>
          <th>性别</th>
          <td>{{ genderText }}</td>
        </tr>
        <tr>
          <th>出生日期</th>
          <td>{{ formatDate(resume.birthDate) }}</td>
        </tr>
        <tr>
          <th>地址</th>
          <td>{{ resume.address || '—' }}</td>
        </tr>
        <tr>
          <th>求职意向</th>
          <td>{{ resume.jobIntention }}</td>
        </tr>
        <tr>
          <th>教育经历</th>
          <td>{{ resume.education }}</td>
        </tr>
        <tr>
          <th>工作经历</th>
          <td>{{ resume.workExperience }}</td>
        </tr>
        <tr>
          <th>自我介绍</th>
          <td>{{ resume.introduction }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import print from 'vue-print-nb'

export default {
  name: 'PrintResume',
  directives: { print },
  props: {
    resume: {
      type: Object,
      required: true
    }
  },
  computed: {
    genderText() {
      if (this.resume.gender === '0') return '男'
      if (this.resume.gender === '1') return '女'
      return '—'
    }
  },
  methods: {
    formatDate(date) {
      if (!date) return '—'
      return new Date(date).toLocaleDateString()
    },
    formatDateTime(datetime) {
      if (!datetime) return '—'
      const d = new Date(datetime)
      return d.toLocaleString()
    }
  }
}
</script>

<style scoped>
table {
  border-collapse: collapse;
}

th {
  background-color: #f2f2f2;
  width: 20%;
  text-align: left;
}

td {
  width: 80%;
}

button {
  margin-bottom: 20px;
}
</style>
