var gulp = require('gulp'),
  eslint = require('gulp-eslint'),
  concat = require('gulp-concat'),
  uglify = require('gulp-uglify');

function swallowError(error) {
  console.log(error.messageFormatted);
}

gulp.task('watch', function() {
  gulp.watch('private/js/**/*.js', ['eslint', 'uglify']);
});

gulp.task('eslint', function() {
  return gulp.src(['private/js/**/*.js'])
    .pipe(eslint())
    .pipe(eslint.format())
    .pipe(eslint.failAfterError());
});

gulp.task('uglify', function() {
  return gulp
    .src([
      'node_modules/jquery/dist/jquery.js',
      'private/js/**/*.js'
    ])
    .pipe(concat('combined.min.js'))
    .pipe(uglify({
      mangle: true,
      compress: true
    }))
    .on('error', swallowError)
    .pipe(gulp.dest('public/js/'))
});

gulp.task('default', [
  'eslint', 'uglify'
]);