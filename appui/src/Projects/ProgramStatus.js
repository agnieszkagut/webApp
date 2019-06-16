import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class ProgramStatus extends Component{
constructor(props){
    super(props)
    this.state={
        status: ""
    }
    this.onClickOne = this.onClickOne.bind(this);
    this.onClickTwo = this.onClickTwo.bind(this);
    this.onClickThree = this.onClickThree.bind(this);
    this.onClickFour = this.onClickFour.bind(this);
    this.onClickFive = this.onClickFive.bind(this);
    this.updateStatus = this.updateStatus.bind(this);
}
    componentDidMount(): void {
        const URL = "http://localhost:8080"
        fetch(URL + "/projects/" + 1,{
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then(res => res.json())
            .then(data => {
                console.log(data)
                this.setState({ status: data.status })
            });
    }
    updateStatus(){
        fetch('http://localhost:8080/projects/status/' + this.state.status, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        })
    }
    onClickOne(){
        if(this.props.credentials.userLevel==1){this.setState({status: "IDENTYFIKOWANIE"})
            this.updateStatus()}
    }
    onClickTwo(){
        if(this.props.credentials.userLevel==1){this.setState({status: "DEFINIOWANIE"})
        this.updateStatus()}
    }
    onClickThree(){
        if(this.props.credentials.userLevel==1){this.setState({status: "ZARZĄDZANIE TRANSZĄ"})
        this.updateStatus()}
    }
    onClickFour(){
        if(this.props.credentials.userLevel==1){this.setState({status: "WDRAŻANIE I REALIZACJA"})
        this.updateStatus()}
    }
    onClickFive(){
        if(this.props.credentials.userLevel==1){this.setState({status: "ZAMYKANIE"})
        this.updateStatus()}
    }
    render(){
    let STATS=[
        {status: "IDENTYFIKOWANIE"},
        {status: "DEFINIOWANIE"},
        {status: "ZARZĄDZANIE TRANSZĄ"},
        {status: "WDRAŻANIE I REALIZACJA"},
        {status: "ZAMYKANIE"}
    ]
        return(
            <div>
                <h2>
                    {this.props.t('project.programStatus', {framework: "react-i18next"})}
                    {("IDENTYFIKOWANIE"===this.state.status)
                        ?(<button className="btn btn-warning" onClick={()=>{
                            this.onClickOne()
                        }}>
                            IDENTYFIKOWANIE
                        </button>)
                        :(<button className="btn btn-default" onClick={()=>{
                            this.onClickOne()
                        }}>
                            IDENTYFIKOWANIE
                        </button>)}
                    {("DEFINIOWANIE"===this.state.status)
                        ?(<button className="btn btn-warning" onClick={()=>{
                            this.onClickTwo()
                        }}>
                            DEFINIOWANIE
                        </button>)
                        :(<button className="btn btn-default" onClick={()=>{
                            this.onClickTwo()
                        }}>
                            DEFINIOWANIE
                        </button>)}
                    {("ZARZĄDZANIE TRANSZĄ"===this.state.status)
                        ?(<button className="btn btn-warning" onClick={()=>{
                            this.onClickThree()
                        }}>
                            ZARZĄDZANIE TRANSZĄ
                        </button>)
                        :(<button className="btn btn-default" onClick={()=>{
                            this.onClickThree()
                        }}>
                            ZARZĄDZANIE TRANSZĄ
                        </button>)}
                    {("WDRAŻANIE I REALIZACJA"===this.state.status)
                        ?(<button className="btn btn-warning" onClick={()=>{
                            this.onClickFour()
                        }}>
                            WDRAŻANIE I REALIZACJA
                        </button>)
                        :(<button className="btn btn-default" onClick={()=>{
                            this.onClickFour()
                        }}>
                            WDRAŻANIE I REALIZACJA
                        </button>)}
                    {("ZAMYKANIE"===this.state.status)
                        ?(<button className="btn btn-warning" onClick={()=>{
                            this.onClickFive()
                        }}>
                            ZAMYKANIE
                        </button>)
                        :(<button className="btn btn-default" onClick={()=>{
                            this.onClickFive()
                        }}>
                            ZAMYKANIE
                        </button>)}
                </h2>
            </div>
        )
    }
}
export default translate('common')(ProgramStatus);