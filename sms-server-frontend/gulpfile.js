var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var ngAnnotate = require('gulp-ng-annotate');
var tempCache = require('gulp-angular-templatecache');
var minifyHtml = require('gulp-minify-html');
var inject = require('gulp-inject');
var watch = require('gulp-watch');

gulp.task('libs-js', function () {
  gulp.src('webapp/resources/libs/**/*.js')
  .pipe(concat('vendors.js'))
  .pipe(ngAnnotate())
  .pipe(uglify())
  .pipe(gulp.dest('./.tmp/js'));
});

gulp.task('css', concatCss);

function concatCss() {
  gulp.src('webapp/**/*.css')
  .pipe(concat('styles.css'))
  .pipe(gulp.dest('./.tmp/styles'));
}

gulp.task('scripts-js', buildScripts);

function buildScripts() {
  gulp.src(['webapp/resources/sms-server/sms-server.js', 'webapp/resources/sms-server/**/*.js'])
  .pipe(concat('app.js'))
  .pipe(ngAnnotate())
  .pipe(uglify())
  .pipe(gulp.dest('./.tmp/js'));
}

gulp.task('inject', function () {
  gulp.src('webapp/index.html')
  .pipe(inject(gulp.src('./.tmp/js/vendors.js', {read: false}), {ignorePath: '.tmp', addRootSlash: false, name: 'head'}))
  .pipe(inject(gulp.src(['./.tmp/js/app.js', './.tmp/js/templates.js'], {read: false}), {ignorePath: '.tmp', addRootSlash: false}))
  .pipe(inject(gulp.src('./.tmp/styles/styles.css'), {ignorePath: '.tmp', addRootSlash: false}))
  .pipe(gulp.dest('./.tmp'));
});

gulp.task('templatecache', buildTemplates);

function buildTemplates() {
  return gulp.src('webapp/resources/sms-server/**/*.html')
  .pipe(tempCache(
      'templates.js', {
        module: 'sms-server',
        standAlone: false
      }))
  .pipe(gulp.dest('./.tmp/js/'));
}

gulp.task('fonts', copyFonts);

function copyFonts() {
  return gulp.src('./UI/libs/components-font-awesome/fonts/**.*')
  .pipe(gulp.dest('./.tmp/fonts/'));
}

gulp.task('watch-styles', function () {
  return watch('webapp/resources/sms-server/**/*.css', { ignoreInitial: false }, concatCss);
});

gulp.task('watch-scripts', function () {
  return watch('webapp/resources/sms-server/**/*.js', { ignoreInitial: false }, buildScripts);
});

gulp.task('watch-templates', function () {
  return watch('webapp/resources/sms-server/**/*.html', { ignoreInitial: false }, buildTemplates);
});

gulp.task('copy', function () {
  return gulp.src('./tmp')
  .pipe(gulp.dest('../sms-server-api/src/main/webapp/'));
});

gulp.task('build', ['libs-js', 'scripts-js', 'templatecache', 'css', 'fonts', 'inject', 'copy']);

gulp.task('watchers', ['watch-styles', 'watch-scripts', 'watch-templates']);

gulp.task('serve', ['build', 'watchers']);