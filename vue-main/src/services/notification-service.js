export { smallNotification, messageNotification, actionNotification };

function smallNotification(variant, options) {
  let notification = new Notification();
  notification.createNotification('small', {
    variant: variant,
    title: options?.title,
    hasTimer: options?.hasTimer,
    time: options?.time,
  });
}
function messageNotification(variant, options) {
  let notification = new Notification();
  notification.createNotification('message', {
    variant: variant,
    title: options?.title,
    message: options?.message,
    hasTimer: options?.hasTimer,
    hasTimeline: options?.hasTimeline,
    time: options?.time,
  });
}
function actionNotification(title, message, cancelText, submitText) {
  let notification = new Notification();
  notification.createNotification('actions', {
    title: title,
    message: message,
    cancelText: cancelText,
    submitText: submitText,
  });
}
let body = (function () {
  let body;

  return {
    get: function () {
      return body;
    },
    set: function (value) {
      body = value;
    },
  };
})();
let notificationList = (function () {
  let notificationList;

  return {
    get: function () {
      return notificationList;
    },
    set: function (value) {
      notificationList = value;
    },
  };
})();
class Notification {
  constructor() {}
  createNotification(type, options) {
    options?.variant === undefined ? (options.variant = 'usual') : options.variant;

    switch (type) {
      case 'small':
        options?.hasTimer === undefined ? (options.hasTimer = true) : options.hasTimer;
        options?.time === undefined && options.hasTimer ? (options.time = 4) : options.time;
        break;
      case 'message':
        options?.hasTimer === undefined ? (options.hasTimer = true) : options.hasTimer;
        options?.hasTimeline === undefined && options.hasTimer
          ? (options.hasTimeline = true)
          : options.hasTimeline;
        options?.time === undefined && options.hasTimer ? (options.time = 8) : options.time;
        break;
      case 'actions':
        options?.title === undefined ? (options.title = 'Сообщение') : options.title;
        options?.cancelText === undefined ? (options.cancelText = 'Отмена') : options.cancelText;
        options?.submitText === undefined ? (options.submitText = 'Ок') : options.submitText;
        break;
    }

    switch (options.variant) {
      case 'usual':
        if (!options?.title) options.title = 'Сообщение';
        break;
      case 'info':
        if (!options?.title) options.title = 'Информация';
        options.icon = `<div class="notification__icon">
                            <div class="icon icon-info"></div>
                        </div>`;
        break;
      case 'success':
        if (!options?.title) options.title = 'Успешно';
        options.icon = `<div class="notification__icon">
                            <div class="icon icon-info"></div>
                        </div>`;
        break;
      case 'warning':
        if (!options?.title) options.title = 'Внимание';
        options.icon = `<div class="notification__icon">
                            <div class="icon icon-info"></div>
                        </div>`;
        break;
      case 'danger':
        if (!options?.title) options.title = 'Ошибка';
        options.icon = `<div class="notification__icon">
                            <div class="icon icon-info"></div>
                        </div>`;
        break;
    }

    let html = `
            <div class="notification__item --${options.variant}">
              <div class="notification__container ${type === 'actions' ? '--actions' : ''}">
                  ${options?.icon ? options.icon : ''}
                  <div class='notification__content'>
                      <h6 class='notification__title'>${options.title}</h6>
                      ${
                        type !== 'small' && options?.message !== undefined
                          ? "<p class='notification__message'>" + options.message + '</p>'
                          : ''
                      }
                  </div>
                  ${
                    type === 'actions'
                      ? '<div class="notification__actions">\n' +
                        '<div class="notification__actions-btn">' +
                        options.cancelText +
                        '</div>\n' +
                        '<div class="notification__actions-btn">' +
                        options.submitText +
                        '</div>\n</div>'
                      : ''
                  }
              </div>
              <div class="notification__close-btn">
              </div>
              ${
                options?.hasTimeline === true ? '<div class="notification__timeline"></div>' : ''
              }
            </div>`;

    this.#appendNotification(html, options.time, options.hasTimeline);
  }
  #accessBodyInstance = () => {
    if (!body.get()) {
      body.set(document.querySelector('body'));
    }
  };
  #accessNotificationListInstance = () => {
    if (!notificationList.get()) {
      if (!body.get()) this.#accessBodyInstance();
      let list = document.createElement('div');
      list.className = 'notification__list';
      body.get().append(list);
      notificationList.set(list);
    }
  };
  #appendNotification = (notificationHtml, time, hasTimeline) => {
    if (hasTimeline === undefined) hasTimeline = false;
    if (!notificationList.get()) this.#accessNotificationListInstance();

    if (notificationList.get() && notificationHtml) {
      notificationList.get().insertAdjacentHTML('afterbegin', notificationHtml);
      this.notification = notificationList.get().firstElementChild;
      this.closeBtn = this.notification.querySelector('.notification__close-btn');
      this.closeBtn.addEventListener('click', this.#closeBtnOnClick);
      setTimeout(() => this.notification.classList.add('--show'), 400);
      this.#swipeController();

      if (hasTimeline && time) {
        this.timeline = this.notification.querySelector('.notification__timeline');
        this.timeline.style.animation = `notificationTimeline ${time}s 0.2s linear forwards`;
        this.timeline.addEventListener('animationend', this.#timelineOnAnimationEnd);
        this.notification.classList.add('--timer');

        if (this.timeline) {
          this.notification.addEventListener('mouseenter', this.#notificationOnMouseEnter);
          this.notification.addEventListener('mouseleave', this.#notificationOnMouseLeave);
        }
      } else if (!hasTimeline && time) {
        setTimeout(() => this.#deleteNotification(), time * 1000 + 100);
      }
    }
  };
  #swipeController = () => {
    let deltaX, deltaY, startX, startY;
    const breakpoint = 120;

    const notificationOnTouchStart = (e) => {
      startX = e.touches[0].clientX;
      startY = e.touches[0].clientY;
    };

    const notificationOnTouchMove = (e) => {
      let currentX = e.touches[0].clientX;
      let currentY = e.touches[0].clientY;
      deltaX = currentX - startX;
      deltaY = currentY - startY;
      if (Math.abs(deltaX) > Math.abs(deltaY)) {
        if (deltaX > 0) {
          this.notification.style.transform = `translateX(${deltaX}px)`;
          if (deltaX > breakpoint) {
            hideNotificationOnSwipe();
          }
        }
      }
    };

    const hideNotificationOnSwipe = () => {
      this.notification.style.animation = 'hideOnSwipe .4s ease forwards';
      setTimeout(() => {
        deleteMobileNotification();
      }, 400);
    };

    const notificationOnTouchEnd = () => {
      if (deltaX) {
        if (deltaX <= breakpoint) {
          this.notification.style.transform = 'translateX(0)';
        } else {
          hideNotificationOnSwipe();
        }
      }
    };

    const deleteMobileNotification = () => {
      this.notification.removeEventListener('touchstart', notificationOnTouchStart);
      this.notification.removeEventListener('touchmove', notificationOnTouchMove);
      this.notification.removeEventListener('touchend', notificationOnTouchEnd);
      this.#deleteNotification();
    };

    if (!window.matchMedia('(min-width: 1200px)').matches) {
      this.notification.addEventListener('touchstart', notificationOnTouchStart);
      this.notification.addEventListener('touchmove', notificationOnTouchMove);
      this.notification.addEventListener('touchend', notificationOnTouchEnd);
    }
  };
  #closeBtnOnClick = () => {
    this.#deleteNotification();
  };
  #timelineOnAnimationEnd = () => {
    setTimeout(() => this.#deleteNotification(), 100);
  };
  #notificationOnMouseEnter = () => {
    this.timeline.style.animationPlayState = 'paused';
  };
  #notificationOnMouseLeave = () => {
    this.timeline.style.animationPlayState = 'running';
  };
  #deleteNotification = () => {
    this.notification.classList.add('--hide');
    setTimeout(() => this.notification.remove(), 300);
    this.#removeEventListeners();
  };
  #removeEventListeners = () => {
    this.closeBtn.removeEventListener('click', this.#closeBtnOnClick);
    if (this.timeline) {
      this.timeline.removeEventListener('animationend', this.#timelineOnAnimationEnd);
      this.notification.removeEventListener('mouseenter', this.#notificationOnMouseEnter);
      this.notification.removeEventListener('mouseleave', this.#notificationOnMouseLeave);
    }
  };
}
