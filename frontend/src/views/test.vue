<template>
    <div>
        <div>
    <full-page ref="fullpage" :options="options" id="fullpage">
    <div class="section">
      First section ...
    </div>
    <div class="section">
      Second section ...
    </div>
  </full-page>
</div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            options: {
        licenseKey: 'YOUR_KEY_HEERE',
        menu: '#menu',
        anchors: ['page1', 'page2', 'page3'],
        sectionsColor: ['#41b883', '#ff5f45', '#0798ec']
      },
        }
    },
    methods: {
  // Called when your components are ready. That is up to you to decide when.
  componentsReady() {
    this.$refs.fullpage.init()
  }
}
}
</script>