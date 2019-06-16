import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import Files from 'react-files'
import Prompt from "./Prompt";

class NewProjectFile extends  Component{
    constructor(props){
        super(props)
        this.state={
            jsonFile: {},
        }
        this.onCancel = this.onCancel.bind(this);
    }

    onFilesChange(files) {
        var file = files[0];
        var reader = new FileReader();
        reader.onload = ()=>{
            fetch('http://localhost:8080/projects', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                },
                body: reader.result
            })
            this.props.callbackFromParent()
        }
        reader.readAsText(file)
    }

    onFilesError(error, file) {
    console.log('error code ' + error.code + ': ' + error.message)
    }
    onCancel(){
        this.props.callbackFromParent()
    }

    render(){
        return (
            <div className="files">
                <Prompt/>
                <Files
                    className='files-dropzone'
                    onChange={this.onFilesChange}
                    onError={this.onFilesError}
                    accepts={['.json']}
                    multiple={false}
                    maxFiles={1}
                    maxFileSize={10000000}
                    minFileSize={0}
                    clickable
                    credentials={this.props.credentials}
                    callbackFromParent={this.props.callbackFromParent}
                >
                    <div>
                        <img style={{cursor:"pointer"}} src={"./img/upload.png"}/>
                    </div>
                </Files>
                <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=>{
                    this.onCancel()}}>
                    {this.props.t('buttons.cancel', { framework: "react-i18next" })}
                </button>
                <br/>
                <br/>
                <br/>
            </div>
        )
    }
}

export default translate('common')(NewProjectFile);