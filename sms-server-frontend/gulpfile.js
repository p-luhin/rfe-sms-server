var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var ngAnnotate = require('gulp-ng-annotate');
var tempCache = require('gulp-angular-templatecache');
var inject = require('gulp-inject');
var watch = require('gulp-watch');
var angularFilesort = require('gulp-angular-filesort');

const SOURCE_FOLDER = 'webapp/resources';
const TARGET_FOLDER = './.tmp/META-INF/resources';

//temp solution
gulp.task('libs-js', function () {
  return gulp.src([SOURCE_FOLDER + '/libs/angular/angular.min.js',
    SOURCE_FOLDER + '/libs/angular/angular-route.min.js',
    SOURCE_FOLDER + '/libs/angular/angular-cookies.min.js',
    SOURCE_FOLDER + '/libs/angular/angular-md5.min.js',
    SOURCE_FOLDER + '/libs/angular/angular-sanitize.min.js',
    SOURCE_FOLDER + '/libs/angular/ng-tags-input.min.js',
    SOURCE_FOLDER + '/libs/angular/ngprogress.min.js',
    SOURCE_FOLDER + '/libs/angular/ngstorage.min.js',
    SOURCE_FOLDER + '/libs/angular/toaster.min.js',
    SOURCE_FOLDER + '/libs/angular/ui-bootstrap.min.js',
    SOURCE_FOLDER + '/libs/angular/ui-bootstrap.tpls.min.js',
    SOURCE_FOLDER + '/libs/angular/ui-select.min.js',
    SOURCE_FOLDER + '/libs/*/*.js'])
  .pipe(concat('vendors.js'))
  .pipe(ngAnnotate())
  .pipe(uglify())
  .pipe(gulp.dest(TARGET_FOLDER + '/js'));
});

gulp.task('css', concatCss);

function concatCss() {
  return gulp.src('webapp/**/*.css')
  .pipe(concat('styles.css'))
  .pipe(gulp.dest(TARGET_FOLDER + '/styles'));
}

gulp.task('scripts-js', buildScripts);

function buildScripts() {
  return gulp.src('webapp/resources/sms-server/**/*.js')
  .pipe(angularFilesort())
  .pipe(concat('app.js'))
  .pipe(ngAnnotate())
  .pipe(uglify())
  .pipe(gulp.dest(TARGET_FOLDER + '/js'));
}

gulp.task('inject', ['libs-js', 'scripts-js', 'templatecache', 'css', 'fonts'],
    function () {
      gulp.src('webapp/index.html')
      .pipe(inject(gulp.src(TARGET_FOLDER + '/js/vendors.js', {read: false}), {
        ignorePath: '.tmp/META-INF/resources',
        addRootSlash: false,
        name: 'head'
      }))
      .pipe(inject(gulp.src(
          [TARGET_FOLDER + '/js/app.js', TARGET_FOLDER + '/js/templates.js'],
          {read: false}),
          {ignorePath: '.tmp/META-INF/resources', addRootSlash: false}))
      .pipe(inject(gulp.src(TARGET_FOLDER + '/styles/styles.css'),
          {ignorePath: '.tmp/META-INF/resources', addRootSlash: false}))
      .pipe(gulp.dest(TARGET_FOLDER));
    });

gulp.task('templatecache', buildTemplates);

function buildTemplates() {
  return gulp.src('webapp/resources/sms-server/**/*.html')
  .pipe(tempCache(
      'templates.js', {
        module: 'sms-server',
        standAlone: false
      }))
  .pipe(gulp.dest(TARGET_FOLDER + '/js/'));
}

gulp.task('fonts', copyFonts);

function copyFonts() {
  return gulp.src('./webapp/resources/fonts/*/**.*')
  .pipe(gulp.dest(TARGET_FOLDER + '/fonts/'));
}

gulp.task('watch-styles', function () {
  return watch('webapp/resources/sms-server/**/*.css', {ignoreInitial: false},
      concatCss);
});

gulp.task('watch-scripts', function () {
  return watch('webapp/resources/sms-server/**/*.js', {ignoreInitial: false},
      buildScripts);
});

gulp.task('watch-templates', function () {
  return watch('webapp/resources/sms-server/**/*.html', {ignoreInitial: false},
      buildTemplates);
});

gulp.task('build', ['inject']);

gulp.task('watchers', ['watch-styles', 'watch-scripts', 'watch-templates']);

gulp.task('serve', ['build', 'watchers']);