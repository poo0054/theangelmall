<template>
	<el-main>
		<el-alert style="margin-bottom:20px;"
				  title="根据角色配置,可让不同角色访问不同的控制台视图,参数值在登录成功后返回 dashboard:{type}" type="success"></el-alert>
		<el-row :gutter="15">
			<el-col :lg="24">
				<el-card header="我的常用" shadow="never">
					<myapp></myapp>
				</el-card>
			</el-col>
		</el-row>
	</el-main>
</template>

<script>
import myapp from './components/myapp';

export default {
	components: {
		myapp
	},
	data() {
		return {}
	},
	mounted() {
		this.$emit('on-mounted')
	},
	methods: {}
}
</script>

<style scoped>

</style>
