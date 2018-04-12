$(document).ready(function(){
  $('.nav-item').hover(function(){
    src = $(this).children('assets/img').attr('src');
    img = src.split('.');
    new_img = img{0}+'_hover.png';
    $(this).children('assets/img').attr('src', new_img);
  }, function(){
    $(this).children('assets/img').attr('src', src);
  });
});
