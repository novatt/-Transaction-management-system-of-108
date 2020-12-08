<template>
  <div>
    <div class="main">
      <div class="stars">
        <div class="star" v-for="(item,index) in starsCount" :key="index" ref="star"></div>
      </div>
      <div class="main-title">标题标题标题</div>
      <div class="ad-info">
        介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容<br>
        介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容介绍内容
      </div>
      <ball></ball>
    </div>
  </div>
</template>

<script>
import ball from './ball.vue'

export default {
  components:{ball},
  data() {
    return {
      starsCount:800,//数量
      distance:600,//间距
    }
  },
  mounted() {
    let starArr=this.$refs.star
    starArr.forEach(item=>{
      var speed = 0.2+(Math.random()*1)
      var distance = this.distance+(Math.random()*300)
      item.style.transformOrigin=`0 0 ${distance}px`
      item.style.transform=`translate3d(0,0,-${distance}px) rotateY(${(Math.random()*360)}deg) rotateX(${(Math.random()*-50)}deg) scale(${speed},${speed})`
    })
  },
}
</script>

<style lang="scss" scoped>
.main{
  width: 100%;
  min-width: 1200px;
  height: 528px;
  background-color: #fff;
  background: radial-gradient(ellipse at bottom, #1b2735 0%, #090a0f 100%);
  overflow: hidden;
  filter: drop-shadow(0 0 10px white);
  position: relative;
  .main-title{
    position: absolute;
    left: 50%;
    top: 70px;
    transform: translateX(-50%);
    font-size:30px;
    color:rgba(255,255,255,1);
    &::after{
      content: '';
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      bottom: -28px;
      width:28px;
      height:4px;
      background:rgba(244,54,63,1);
      border-radius:2px;
    }
  }
  .stars{
    transform: perspective(500px);
    transform-style: preserve-3d;
    position: absolute;
    perspective-origin: 50% 100%;
    left: 50%;
    animation: rotate 90s infinite linear;
    bottom: -256px;
    .star{
      width: 2px;
      height: 2px;
      background: #f7f7b8;
      position: absolute;
      top: 0;
      left: 0;
      backface-visibility: hidden;
    }
    @keyframes rotate {
      0%{
          transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
      }
      100%{
          transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(-360deg);
      }
    }
  }
  .ad-info{
    font-size: 15px;
    line-height: 24px;
    margin-top: 180px;
    color: rgba($color: #fff, $alpha: .8);
    text-align: center;
  }

  .dot-num{
    // position: relative;
    // height: 200px;
    // width: 100%;
    .dot{
      position: absolute;
      top: 0;
      display: inline-block;
      .dot-title {
        font-size: 40px;
        line-height: 37px;
        font-weight: bold;
        color: #f23340;
      }
      .dot-describe {
        margin-top: 10px;
        line-height: 20px;
        color: rgba($color: #fff, $alpha: .8);
        font-size: 14px;
      }
    }
  }
}
</style>
