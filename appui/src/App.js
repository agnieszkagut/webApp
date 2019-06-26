import React, { Component } from "react";
import "./App.css";
import "bootswatch/yeti/bootstrap.css";
import { translate, Trans } from 'react-i18next';
import { Navbar, NavItem, Nav, Grid, Row, Col } from "react-bootstrap";
import DisplayComponent from "./Display/DisplayComponent";
import Time from "./Display/Time";
import Login from "./Login/Login";
import Media from 'react-media';

class App extends Component {
    constructor() {
        super();
        this.state = {
            showSmallMenu: false,
            activeComp: 0,
            activeUser: {},
            theme: "navbar navbar-inverse",
            isLogged: false,
            credentials:{
                username: "",
                password: "",
                userLevel: 4
            }
        }
        this.myCallback = this.myCallback.bind(this);
        this.logOut = this.logOut.bind(this);
        this.refresh = this.refresh.bind(this);
    }
    refresh=(prev)=>{
        this.setState({activeComp: 7})
        this.setState({activeComp: prev})
    }
    logOut(){
        this.setState({userLevel: 4})
        this.setState({username: ""})
        this.setState({isLogged: false})
    }
    myCallback = (data, username, password) => {
        this.setState({ userLevel: data })
        this.setState({password: ""})
        const credentials = this.state.credentials
        credentials.username = username
        credentials.password = password
        credentials.userLevel = data
        this.setState({ credentials: credentials })
        this.setState({ isLogged: true })
        const URL = "http://localhost:8080"
        fetch(URL + "/users/userInfo/" + username,
            {
                method: 'GET',
                headers: {
                    'Authorization': 'Basic ' + btoa(username + ":" + password)
                }
            }).then(res => res.json())
            .then(data => {
                this.setState({ activeUser: data })
            });
    }
    render() {
        const theme = this.state.theme + " navbar-fixed-top"
        const themeDown = this.state.theme + " navbar-fixed-bot"
        const buttonStyle = this.state.theme + " btn btn-primary"
        const buttonStyleDisabled = this.state.theme + " btn btn-primary disabled"
        const { t, i18n } = this.props;
        const activeComp = this.state.activeComp;
        const changeLanguage = (lng) => {
            i18n.changeLanguage(lng);
        }
        const changeTheme = () => {
            if(this.state.theme == "navbar navbar-inverse"){
                this.setState({theme: "navbar navbar-default"})
            }
            else{
                this.setState({theme: "navbar navbar-inverse"})
            }
        }
        let COMPNAMES
        (this.state.credentials.userLevel === 0)
        ?COMPNAMES = [
                { compName: this.props.t('compnames.main', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.projects', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.reported', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.add', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.calendar', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.messages', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.newUser', { framework: "react-i18next" })}
            ]
            :COMPNAMES = [
                { compName: this.props.t('compnames.main', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.projects', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.reported', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.add', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.calendar', { framework: "react-i18next" })},
                { compName: this.props.t('compnames.messages', { framework: "react-i18next" })}
            ]
        let window
            this.state.isLogged
                ?window=<div>
                        <div position={"absolute"}
                             style={{marginTop: "0px"}}>
                            <Grid>
                                <Media query="(max-width: 450px)">
                                    {matches =>
                                        matches ? (
                                            <div className="nav nav-pills">
                                                <div className="nav-item dropdown">
                                                    <button className={buttonStyle} onClick={() => this.setState({showSmallMenu: !this.state.showSmallMenu})}>Menu</button>
                                                    {this.state.showSmallMenu
                                                    ?<div className="dropdown-menu show" x-placement="bottom-start"
                                                         style={{position: "absolute", transform: "translate3d(0px, 38px, 0px)", top: "0px", left: "0px", will_change: "transform"}}>
                                                        <Nav
                                                            bsStyle="pills"
                                                            activeKey={activeComp}
                                                            onSelect={index => {
                                                                this.setState({ activeComp: index });
                                                            }}
                                                        >
                                                            {COMPNAMES.map((compName, index) => (
                                                                <NavItem key={index} eventKey={index}>{compName.compName}</NavItem>
                                                            ))}
                                                            <NavItem onClick={() => this.logOut()}>
                                                                { this.props.t('buttons.logOut', { framework: "react-i18next" })}
                                                            </NavItem>
                                                            <NavItem style={{marginLeft:"50px", border: "2px solid white"}}>
                                                                {this.props.t('logged', { framework: "react-i18next" })}
                                                                <u><b>{this.state.activeUser.username}({this.state.activeUser.position})</b></u>
                                                            </NavItem>
                                                        </Nav>
                                                    </div>
                                                        :null}
                                                </div>
                                            </div>
                                        ) : (
                                            <div>
                                                <Navbar
                                                    className={theme}
                                                >
                                                    <Navbar.Header>
                                                        <Row>
                                                            <Col >
                                                                <Nav
                                                                    bsStyle="pills"
                                                                    activeKey={activeComp}
                                                                    onSelect={index => {
                                                                        this.setState({ activeComp: index });
                                                                    }}
                                                                >
                                                                    {COMPNAMES.map((compName, index) => (
                                                                        <NavItem key={index} eventKey={index}>{compName.compName}</NavItem>
                                                                    ))}
                                                                    <NavItem onClick={() => this.logOut()}>
                                                                        { this.props.t('buttons.logOut', { framework: "react-i18next" })}
                                                                    </NavItem>
                                                                    <NavItem style={{marginLeft:"50px", border: "2px solid white"}}>
                                                                        {this.props.t('logged', { framework: "react-i18next" })}
                                                                        <u><b>{this.state.activeUser.username}({this.state.activeUser.position})</b></u>
                                                                    </NavItem>
                                                                </Nav>
                                                            </Col>
                                                        </Row>
                                                    </Navbar.Header>
                                                </Navbar>
                                            </div>
                                        )
                                    }
                                </Media>
                                <Media query="(max-width: 1200px)">
                                    {matches =>
                                        matches ? (
                                            <Media query="(min-width: 449px)">
                                                {matches =>
                                                    matches ? (
                                                        <Row style={{marginTop: "100px"}}>
                                                            <DisplayComponent user={this.state.activeUser.userId}
                                                                              index={activeComp}
                                                                              buttonStyle={buttonStyle}
                                                                              buttonStyleDisabled={buttonStyleDisabled}
                                                                              credentials={this.state.credentials}
                                                                              refresh={this.refresh}
                                                            />
                                                        </Row>
                                                    ) : (
                                                        <Row style={{marginTop: "0px"}}>
                                                            <DisplayComponent user={this.state.activeUser.userId}
                                                                              index={activeComp}
                                                                              buttonStyle={buttonStyle}
                                                                              buttonStyleDisabled={buttonStyleDisabled}
                                                                              credentials={this.state.credentials}
                                                                              refresh={this.refresh}
                                                            />
                                                        </Row>
                                                    )
                                                }
                                            </Media>
                                        ) : (
                                            <Row style={{marginTop: "50px"}}>
                                                <DisplayComponent user={this.state.activeUser.userId}
                                                                  index={activeComp}
                                                                  buttonStyle={buttonStyle}
                                                                  buttonStyleDisabled={buttonStyleDisabled}
                                                                  credentials={this.state.credentials}
                                                                  refresh={this.refresh}
                                                />
                                            </Row>
                                        )
                                    }
                                </Media>
                            </Grid>
                        </div>

                        <div className="text-center" style={{display:"block"}}>
                            {/*<div>
                                <button className={buttonStyle} onClick={() => changeTheme()}>{this.props.t('theme', { framework: "react-i18next" })}</button>
                            </div>*/}
                            <div>
                                <button className={buttonStyle} onClick={() => changeLanguage('pl')}><img src="./img/pl.png" alt="PL"/></button>
                                <button className={buttonStyle} onClick={() => changeLanguage('en')}><img src="./img/en.png" alt="EN"/></button>
                            </div>
                            <div>
                                <Time buttonStyle={buttonStyle}/>
                            </div>
                        </div>
                </div>
                :window=<div>
                    <Login
                        callbackFromParent={this.myCallback}
                    />
                </div>
        return (
            <div>
                {window}
            </div>
        );
    }
}


export default translate('common')(App);