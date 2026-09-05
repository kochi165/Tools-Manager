'use strict';

const dialogs = {
  login: document.getElementById('log-dialog'),
  register: document.getElementById('regi-dialog')
};
const forms = {
  login: document.getElementById('log-form'),
  register: document.getElementById('regi-form')
};
const error_messages = {
  login: document.getElementById('log-error-message'),
  register: document.getElementById('regi-error-message')
};
const param = new URLSearchParams(window.location.search);

//エラーメッセージ
{
  if (param.has('error')) {
    history.replaceState(null, '', window.location.pathname);
    error_messages['login'].textContent = '入力情報が不正です';
    dialogs['login'].showModal();
  } else {
    error_messages['login'].textContent = '';
  }
}

//ウィンドウポップアップ
{
  document.querySelectorAll('.account-button').forEach((button) => {
    button.addEventListener('click', (e) => {
      dialogs[e.currentTarget.dataset.type].showModal();
    });
  });

  document.querySelectorAll('.exit').forEach((button) => {
    button.addEventListener('click', (e) => {
      const datatype = e.currentTarget.dataset.type;
      error_messages[datatype].textContent = '';
      forms[datatype].reset();
      dialogs[datatype].close();
    });
  });

  document.querySelectorAll('dialog').forEach((dialog) => {
    dialog.addEventListener('click', (e) => {
      const rect = dialog.getBoundingClientRect();

      const isInDialog =
        rect.top <= e.clientY &&
        e.clientY <= rect.bottom &&
        rect.left <= e.clientX &&
        e.clientX <= rect.right;

      if (!isInDialog) {
        dialog.close();
      }
    });
  });
}

const inputName = document.getElementById('regi-Name');

const inputPass = document.getElementById('regi-Pass');

//httpリクエスト処理
async function accountControl() {
  try {
    const response = await fetch(
      'http://localhost:8080/accountController/register',
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify({
          username: inputName.value,
          password: inputPass.value
        })
      }
    );
  } catch (error) {
    console.error('error');
  }
}

//新規登録
{
  document.querySelectorAll('.entry').forEach((entry) => {
    entry.addEventListener('click', (e) => {
      if (document.getElementById('regi-Pass2').value !== inputPass.value) {
        error_messages['register'].textContent = 'パスワードが異なっています';
        return;
      } else {
        error_messages['register'].textContent = '';
      }
      accountControl();
    });
  });
}

let command;
//分岐
{
  const status = {
    tool: null,
    mode: null,
    operation: null
  };

  const toolElements = {
    ffmpeg: {
      beginner: document.getElementById('ffmpeg-beginner'),
      developer: document.getElementById('ffmpeg-developer')
    }
  };

  function modeChange(mode) {
    if (!status.tool) {
      return;
    }
    document.querySelectorAll('.questions-mode').forEach((section) => {
      section.style.display = 'none';
    });
    toolElements[status.tool][mode].style.display = 'block';
  }

  function opeChange(operation) {
    if (!toolElements[status.tool]?.[status.mode]) {
      return;
    }

    const currentElement =
      toolElements[status.tool][status.mode].querySelectorAll(
        '.operation-option'
      );

    currentElement.forEach((div) => {
      div.style.display = 'none';

      const form = div.querySelector(`div[data-operation="${operation}"]`);

      if (form) {
        div.closest('.transition').style.display = 'flex';
        div.style.display = 'block';
      }
    });
  }

  function createData(div) {
    const formData = {};
    div.querySelectorAll('[data-option]').forEach((input) => {
      formData[input.dataset.option] = input.value;
    });
    return formData;
  }

  function writing(formData) {
    command = JSON.stringify({
      process: status.operation,
      option: formData
    });

    console.log(command);
  }

  //ツール取得
  document.querySelectorAll('details').forEach((detail) => {
    detail.addEventListener('toggle', () => {
      if (detail.open) {
        status.tool = detail.dataset.tool;
      }
    });
  });

  //モード取得
  document.querySelectorAll('.mode-options input').forEach((input) => {
    input.addEventListener('change', (e) => {
      const mode = e.currentTarget.value;
      status.mode = mode;
      modeChange(mode);
    });
  });

  //オペレーション取得
  document.querySelectorAll('input[name="operation"]').forEach((input) => {
    input.addEventListener('change', (e) => {
      const operation = e.currentTarget.value;
      status.operation = operation;
      opeChange(operation);
    });
  });

  //オプション取得
  document.querySelectorAll('.operation-option div').forEach((div) => {
    if (!div) {
      console.log('formなし:', div);
      return;
    }

    div.addEventListener('submit', (e) => {
      e.preventDefault();

      writing(createData(div));
    });
  });
}
//httpリクエスト
/*
async function commandControl() {}
*/
