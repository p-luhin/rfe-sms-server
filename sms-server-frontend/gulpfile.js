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
const TARGET_WATCHER_FOLDER = 'target/classes/META-INF/resources';


function makeStyles() {
  concatCss(TARGET_FOLDER);
}

function makeScripts() {
  buildScripts(TARGET_FOLDER);
}

function makeTemplates() {
  buildTemplates(TARGET_FOLDER);
}

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

gulp.task('css', makeStyles);

function concatCss(target) {
  return gulp.src('webapp/**/*.css')
  .pipe(concat('styles.css'))
  .pipe(gulp.dest(target + '/styles'));
}

gulp.task('scripts-js', makeScripts);

function buildScripts(target) {
  return gulp.src('webapp/resources/sms-server/**/*.js')
  .pipe(angularFilesort())
  .pipe(concat('app.js'))
  .pipe(ngAnnotate())
  .pipe(uglify())
  .pipe(gulp.dest(target + '/js'));
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

gulp.task('templatecache', makeTemplates);

function buildTemplates(target) {
  return gulp.src('webapp/resources/sms-server/**/*.html')
  .pipe(tempCache(
      'templates.js', {
        module: 'sms-server',
        standAlone: false
      }))
  .pipe(gulp.dest(target + '/js/'));
}

gulp.task('fonts', copyFonts);

function copyFonts() {
  return gulp.src('./webapp/resources/fonts/*/**.*')
  .pipe(gulp.dest(TARGET_FOLDER + '/fonts/'));
}

function watchStyles() {
  return concatCss(TARGET_WATCHER_FOLDER);
}

function watchScripts() {
  return buildScripts(TARGET_WATCHER_FOLDER);
}

function watchTemplates() {
  return buildTemplates(TARGET_WATCHER_FOLDER);
}

gulp.task('watch-styles', function () {
  return watch('webapp/resources/sms-server/**/*.css', {ignoreInitial: false},
      watchStyles);
});

gulp.task('watch-scripts', function () {
  return watch('webapp/resources/sms-server/**/*.js', {ignoreInitial: false},
      watchScripts);
});

gulp.task('watch-templates', function () {
  return watch('webapp/resources/sms-server/**/*.html', {ignoreInitial: false},
      watchTemplates);
});

gulp.task('build', ['inject']);

gulp.task('watchers', ['watch-styles', 'watch-scripts', 'watch-templates']);

gulp.task('serve', ['build', 'watchers']);